#include <WiFi.h>

#define RXp2 16
#define TXp2 17
#define ledPin 32
bool ledStatus = false;  //一開始先讓LED為暗

const char ssid[] = "_"; //自家WiFi網路名稱
const char pwd[] = "_"; //WiFi密碼

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
  //Serial2.begin(9600, SERIAL_8N1, RXp2, TXp2);
  
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
  //Serial.println(Serial2.readString());
  //delay(50);  //每50ms檢查一次從RX跟TX傳來的訊息
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
}