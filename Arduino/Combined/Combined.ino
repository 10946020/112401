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
  //顯示目前儲存了幾個指紋樣本
  Serial.print("Sensor contains "); Serial.print(finger.templateCount); Serial.println(" templates"); 

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
  finger_status = getFingerprintID();
  //狀態2表示沒偵測到指紋, 寫個if防止迴圈一直更新
  if(finger_status != 2){  //偵測到指紋辨識後等getFingerprintID()動作完, 重置狀態
      pwDigit = 0;
      userEnter = "";
      delay(3000);
      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("Waiting...");
      Serial.print("Please enter the password or fingerprint : ");
  }
  else{
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

      if(userEnter == "#114514"){  //進入註冊模式

      }
      else if(userEnter == "#228922"){  //進入刪除身分模式

      }
      else if(pwDigit == password.length()){ //當輸入的密碼位數跟預設密碼的位數一樣時
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
          lcd.print("Wrong password!");
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
  }
}

//---------------------------------------------------------------------------------------------------------------

uint8_t getFingerprintID() {
  uint8_t p = finger.getImage();
  switch (p) {
    case FINGERPRINT_OK:
      //Serial.println("Image taken");
      break;
    case FINGERPRINT_NOFINGER:
      //Serial.println("No finger detected");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_IMAGEFAIL:
      Serial.println("Imaging error");
      return p;
    default:
      Serial.println("Unknown error");
      return p;
  }

  // OK success!

  p = finger.image2Tz();
  switch (p) {
    case FINGERPRINT_OK:
      //Serial.println("Image converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Image too messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      Serial.println("Could not find fingerprint features");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      Serial.println("Could not find fingerprint features");
      return p;
    default:
      Serial.println("Unknown error");
      return p;
  }

  // OK converted!


  //尋找指紋資訊並判斷是哪個身分, 或是掃描到的指紋存不存在
  p = finger.fingerSearch();
  if (p == FINGERPRINT_OK) {  //找到人
    Serial.println("\nFound a print match!");
    Serial.println("Welcome back, User" + String(finger.fingerID));

    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Hello, User" + String(finger.fingerID)); //指紋可以特定身分, 可以直接顯示名字
  }
  else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Communication error");
    return p;
  }
  else if (p == FINGERPRINT_NOTFOUND) {  //沒找到
    Serial.println("\nDid not find a match");

    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Didn't found");
    lcd.setCursor(0, 1);
    lcd.print("fingerprint.");
    return p;
  }
  else {
    Serial.println("Unknown error");
    return p;
  }

  // found a match!
  Serial.print("Found ID #"); Serial.print(finger.fingerID);
  Serial.print(" with confidence of "); Serial.println(finger.confidence);

  return finger.fingerID;
}

/*
uint8_t getFingerprintEnroll() {

  int p = -1;
  Serial.print("Waiting for valid finger to enroll as #"); Serial.println(id);
  while (p != FINGERPRINT_OK) {
    p = finger.getImage();
    switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image taken");
      break;
    case FINGERPRINT_NOFINGER:
      break;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      break;
    case FINGERPRINT_IMAGEFAIL:
      Serial.println("Imaging error");
      break;
    default:
      Serial.println("Unknown error");
      break;
    }
  }

  // OK success!

  p = finger.image2Tz(1);
  switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Image too messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      Serial.println("Could not find fingerprint features");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      Serial.println("Could not find fingerprint features");
      return p;
    default:
      Serial.println("Unknown error");
      return p;
  }
  
  Serial.println("Remove finger");
  delay(2000);
  p = 0;
  while (p != FINGERPRINT_NOFINGER) {
    p = finger.getImage();
  }
  Serial.print("ID "); Serial.println(id);
  p = -1;
  Serial.println("Place same finger again");
  while (p != FINGERPRINT_OK) {
    p = finger.getImage();
    switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image taken");
      break;
    case FINGERPRINT_NOFINGER:
      break;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      break;
    case FINGERPRINT_IMAGEFAIL:
      Serial.println("Imaging error");
      break;
    default:
      Serial.println("Unknown error");
      break;
    }
  }

  // OK success!

  p = finger.image2Tz(2);
  switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Image too messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      Serial.println("Could not find fingerprint features");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      Serial.println("Could not find fingerprint features");
      return p;
    default:
      Serial.println("Unknown error");
      return p;
  }
  
  // OK converted!
  Serial.print("Creating model for #");  Serial.println(id);
  
  p = finger.createModel();
  if (p == FINGERPRINT_OK) {
    Serial.println("Prints matched!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Communication error");
    return p;
  } else if (p == FINGERPRINT_ENROLLMISMATCH) {
    Serial.println("Fingerprints did not match");
    return p;
  } else {
    Serial.println("Unknown error");
    return p;
  }   
  
  Serial.print("ID "); Serial.println(id);
  p = finger.storeModel(id);
  if (p == FINGERPRINT_OK) {
    Serial.println("Stored!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Communication error");
    return p;
  } else if (p == FINGERPRINT_BADLOCATION) {
    Serial.println("Could not store in that location");
    return p;
  } else if (p == FINGERPRINT_FLASHERR) {
    Serial.println("Error writing to flash");
    return p;
  } else {
    Serial.println("Unknown error");
    return p;
  }   
}

uint8_t deleteFingerprint(uint8_t id) {
  uint8_t p = -1;

  p = finger.deleteModel(id);

  if (p == FINGERPRINT_OK) {
    Serial.println("Deleted!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Communication error");
  } else if (p == FINGERPRINT_BADLOCATION) {
    Serial.println("Could not delete in that location");
  } else if (p == FINGERPRINT_FLASHERR) {
    Serial.println("Error writing to flash");
  } else {
    Serial.print("Unknown error: 0x"); Serial.println(p, HEX);
  }

  return p;
}
*/