#include <WiFi.h>
#include <HTTPClient.h>

#define DeviceID 112401
String deviceName = "專題用辨識工具";

#define RXp2 16  //灰色線, ESP32板上的RX2(GIOP16), 接到Arduino板子上的1號
#define TXp2 17  //白色線, ESP32板上的TX2(GIOP17), 接到Arduino板子上的0號

#define ledPin 32  //GIOP32, 連至LED
bool ledStatus = false;  //一開始先讓LED為暗

const char ssid[] = ""; //自家WiFi網路名稱
const char pwd[] = ""; //WiFi密碼

void Show_Wifi_Status(){  //led腳位顯示當前狀態為true or false
  delay(20);
  digitalWrite(ledPin, ledStatus);
}

void Show_Wifi_Data(){  //顯示Wifi訊息用
  Serial.print("IP位址:");
  Serial.println(WiFi.localIP()); //讀取IP位址
  Serial.print("WiFi RSSI:");
  Serial.println(WiFi.RSSI()); //讀取WiFi強度
}

void setup() {
  Serial.begin(115200);

  //第二個Serial, 顯示從16,17腳位接收到的訊息
  //第一個參數的數值需跟Arduino UNO板的baud值相同
  Serial2.begin(9600, SERIAL_8N1, RXp2, TXp2);
  
  pinMode(ledPin, OUTPUT);  //設定為輸出腳位
  Show_Wifi_Status();

  WiFi.mode(WIFI_STA); //設置WiFi模式
  WiFi.begin(ssid,pwd); //Wifi, 啟動

  Serial.print("WiFi connecting");

  //當WiFi連線時會回傳WL_CONNECTED，因此跳出迴圈時代表已成功連線
  while(WiFi.status()!=WL_CONNECTED){
    Serial.print(".");
    delay(500);   
  }
  Serial.println("\nConnected!");
  Show_Wifi_Data();

  ledStatus = true;
  Show_Wifi_Status();
}

void loop() { 
  if(WiFi.status()!=WL_CONNECTED){
    //如果Wifi斷掉連線就讓LED為暗, 然後進入while迴圈直到重新連上Wifi
    ledStatus = false;
    Show_Wifi_Status();
    Serial.println("Wifi disconnected!!!");
    Serial.print("Waiting for reconnection");

    while(WiFi.status()!=WL_CONNECTED){
      Serial.print(".");
      delay(1000); 
    }

    //重新連上後顯示Wifi訊息, 以及讓提示用LED為亮
    Serial.println("Wifi reconnected!");
    Show_Wifi_Data();
    ledStatus = true;
    Show_Wifi_Status();
  }

  //這裡的Serial顯示從Serial2讀取到的訊息
  while(Serial2.available() > 0){
    int userID = Serial2.readString().toInt();  //從Arduino UNO傳來的使用者ID, 再把字串換成int
    Serial.println(userID);
    if(userID == -1){
      Serial.println("This person isn't found.");
    }
    else if(userID <= 0 || userID > 127){
      Serial.println("Error.");
    }
    else{
      String serverRoute = "http://140.131.114.158/Project/Open_Device_Information_Add_Save.asp?UserID=";
      String toWebServer = serverRoute + userID + "&DeviceID=" + DeviceID;
      connect(toWebServer);
      Serial.println("Connected to the server from User " + String(userID));
    }
    delay(100);  //設定每幾ms檢查一次從RX跟TX傳來的訊息
  }
}

void connect(String ipAddress){  //對網址發起get請求的function
  HTTPClient http;  //定義新的http物件
  http.begin(ipAddress);  //準備使用參數填的網址
  int httpCode = http.GET();  //發出GET request
  if(httpCode > 0){  //如果無異常
    if(httpCode == HTTP_CODE_OK){
      //String payload = http.getString();  //查看網頁資訊
      //Serial.println(payload);  //看情況使用, 不然會跑一堆網頁資訊
      Serial.println("GET succeeded.");  //成功
    }
  }
  else{
    Serial.printf("GET failed, error : %s\n", http.errorToString(httpCode).c_str());  //GET失敗就傳送錯誤訊息
  }
  http.end();  //結束動作
  delay(50);
}