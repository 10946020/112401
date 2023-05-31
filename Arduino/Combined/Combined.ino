#include <Adafruit_Fingerprint.h>
#include <Keypad.h>
#include <LiquidCrystal_I2C.h>

//---Sensor---
volatile int finger_status = -1;
SoftwareSerial mySerial(2, 3); // TX/RX on fingerprint sensor. 黃色2, 白色3, 接3.3V
Adafruit_Fingerprint finger = Adafruit_Fingerprint(&mySerial); //啟用指紋辨識物件

//---Keypad---
const byte ROWS = 4; //four rows
const byte COLS = 3; //three columns
char keys[ROWS][COLS] = {
  {'1','2','3'},
  {'4','5','6'},
  {'7','8','9'},
  {'*','0','#'}
};
byte rowPins[ROWS] = {10, 9, 8, 7}; //connect to the row pinouts of the keypad
byte colPins[COLS] = {6, 5, 4}; //connect to the column pinouts of the keypad
//Create an object of keypad
Keypad keypad = Keypad(makeKeymap(keys), rowPins, colPins, ROWS, COLS); //啟用keypad物件
String password = "112401"; //按鈕的固定密碼, 可在這個程式檔案內更改
int pwDigit; //目前已輸入密碼的位元數
String userEnter; //記錄使用者輸入的密碼

//---LCD---
LiquidCrystal_I2C lcd(0x27,16,2);  //設定LCD位置0x27,設定LCD大小為16*2

//---------------------------------------------------------------------------------------------------------------

void setup(){ //開始時執行一次, 主要為偵測
  //---Sensor---
  Serial.begin(9600); //IDE視窗初始化
  while (!Serial);
  delay(100);
  Serial.println("\n\nAdafruit finger detect test");
  // set the data rate for the sensor serial port
  finger.begin(57600);
  if (finger.verifyPassword()) {
    Serial.println("Found fingerprint sensor!");
  } else {
    Serial.println("Did not find fingerprint sensor :(");
    while (1) { delay(1); }
  }
  finger.getTemplateCount();
  Serial.print("Sensor contains "); Serial.print(finger.templateCount); Serial.println(" templates"); //顯示目前儲存了幾個指紋樣本

  //---Keypad---
  pwDigit = 0; //
  userEnter = ""; //清空使用者輸入的密碼
  Serial.println("I'm ready.");
  Serial.println("Waiting for valid finger or to enter the password...");

  //---LCD---
  //SDA接A4; SCL接A5
  //初始化LCD 
  lcd.init(); 
  lcd.backlight(); //開啟背光
  lcd.print("Waiting..."); //顯示器只能顯示英數, 中文會顯示亂碼
}

//---------------------------------------------------------------------------------------------------------------

void loop(){ //重複執行的動作
  char key = keypad.getKey(); // Read the key
  finger_status = getFingerprintIDez();

  //按下按鈕時
  if(key){
    if(pwDigit == 0){ //原本顯示著Waiting, 先清空
      lcd.clear();
    }

    //基本安全設定
    if(pwDigit >= 1){ //輸入至少1碼後再有新的輸入的時候
      for(int i = 0; i < pwDigit; i++){
        //從最左上角開始把前面輸入的密碼都換成*, 避免旁邊的人看到
        lcd.setCursor(i, 0);
        lcd.print("*");
      }
    }

    //從最左上方開始往右顯示按下的按鈕
    lcd.setCursor(pwDigit, 0);
    Serial.print("*"); //IDE視窗
    userEnter += key;
    pwDigit ++;
    lcd.print(key); //LCD

    if(pwDigit == password.length()){ //當輸入的密碼位數跟預設密碼的位數一樣時
        //不管正確與否都要清空,並準備顯示其他訊息
        lcd.clear();
        lcd.setCursor(0, 0); //從第一行最左邊開始顯示

      if(userEnter == password){ //正確
        Serial.println("\nWelcome!");
        //LCD顯示以下訊息
        lcd.print("Validated");
        lcd.setCursor(0, 1); //從第二行最左邊開始顯示
        lcd.print("Welcome!");
      }
      else{
        Serial.println("\nIncorrect Password.");
        lcd.print("Try again!");
      }

      //清空位元數跟輸入的密碼字串, 等待3秒
      pwDigit = 0;
      userEnter = "";
      delay(3000);

      //LCD顯示待機訊息
      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("Waiting...");
      Serial.print("Please enter the password or fingerprint : ");
    }
  }

  //或是不用按鈕, 指紋感測到時
  else if(finger_status!=-1 and finger_status!=-2){
    Serial.println("Match");

    //可以設定感測到哪個指紋時要顯示出什麼訊息
    switch(finger.fingerID){
        case 1:
          Serial.println("Welcome back, User01!");
          lcd.clear();
          lcd.setCursor(0, 0);
          lcd.print("Hello, User01!"); //指紋可以特定身分, 可以直接顯示名字
          break;
        case 2:
          Serial.println("Welcome back, User02!");
          lcd.clear();
          lcd.setCursor(0, 0);
          lcd.print("Hello, User02!");
          break;
        case 3:
          Serial.println("Welcome back, User03!");
          lcd.clear();
          lcd.setCursor(0, 0);
          lcd.print("Hello, User03!");
          break;          
    }

    pwDigit = 0;
    userEnter = "";
    delay(3000);
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Waiting...");
    Serial.print("Please enter the password or fingerprint : ");
  }
}

//---------------------------------------------------------------------------------------------------------------

// returns -1 if failed, otherwise returns ID #
int getFingerprintIDez() {
  uint8_t p = finger.getImage();
  //if (p!=2){Serial.println(p);}
  if (p != FINGERPRINT_OK)  return -1;
  
  p = finger.image2Tz();
  //if (p!=2){Serial.println(p);}
  if (p != FINGERPRINT_OK)  return -1;

  p = finger.fingerFastSearch();
  if (p != FINGERPRINT_OK)  return -2;
  
  // found a match!
  Serial.print("\nFound ID #"); Serial.print(finger.fingerID);
  Serial.print(" with confidence of "); Serial.println(finger.confidence);
  return finger.fingerID; 
}