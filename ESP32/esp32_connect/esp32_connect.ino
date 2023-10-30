#include <WiFi.h>

#define RXp2 16
#define TXp2 17

#define ledPin 32
bool ledStatus = false;

const char ssid[]="_"; //自家WiFi網路名稱
const char pwd[]="_"; //WiFi密碼

void setup() {
  Serial.begin(115200);
  Serial2.begin(9600, SERIAL_8N1, RXp2, TXp2);  //第二個Serial, 顯示從16,17腳位接收到的訊息
  pinMode(ledPin, OUTPUT);
  digitalWrite(ledPin, ledStatus);

  WiFi.mode(WIFI_STA); //設置WiFi模式
  WiFi.begin(ssid,pwd); 

  Serial.println("WiFi connecting");

  //當WiFi連線時會回傳WL_CONNECTED，因此跳出迴圈時代表已成功連線
  while(WiFi.status()!=WL_CONNECTED){
    Serial.print(".");
    delay(500);   
  }
  Serial.println("\nConnected!");
  Serial.print("IP位址:");
  Serial.println(WiFi.localIP()); //讀取IP位址
  Serial.print("WiFi RSSI:");
  Serial.println(WiFi.RSSI()); //讀取WiFi強度

  ledStatus = true;
  digitalWrite(ledPin, ledStatus);
}
void loop() {
  Serial.println(Serial2.readString());
  delay(50);
}