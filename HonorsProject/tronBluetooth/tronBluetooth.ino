#include <Adafruit_NeoPixel.h>
#include <SoftwareSerial.h>
#define PIN 6
int state = 0;

// Parameter 1 = number of pixels in strip
// Parameter 2 = pin number (most are valid)
// Parameter 3 = pixel type flags, add together as needed:
//   NEO_KHZ800  800 KHz bitstream (most NeoPixel products w/WS2812 LEDs)
//   NEO_KHZ400  400 KHz (classic 'v1' (not v2) FLORA pixels, WS2811 drivers)
//   NEO_GRB     Pixels are wired for GRB bitstream (most NeoPixel products)
//   NEO_RGB     Pixels are wired for RGB bitstream (v1 FLORA pixels, not v2)
Adafruit_NeoPixel strip = Adafruit_NeoPixel(100, PIN, NEO_GRB + NEO_KHZ800);
uint32_t tron = strip.Color(46, 185, 255);
char mode;
char data;
bool newMode;

// For bluetooth module
SoftwareSerial bSerial(2, 3); // RX, TX

void setup() {
  strip.begin();
  strip.show(); // Initialize all pixels to 'off'
  strip.setBrightness(25);
  // Set the data rate for the bluetooth serial communication
  bSerial.begin(9600);
  bSerial.println("AT");
  mode = 'p';
  newMode = false;
}

void loop() {
  if (newMode) {
    newMode = false;
  }
  
  if (mode == 'p') {
    pulse(tron, 50, 0);
  } else if (mode == 'c') {
    colorWipe(tron, 30);
  } else if (mode == 'r') {
    rainbow(30);
  } else if (mode == 'a') {
    colorWipe(strip.Color(255,   0,   0), 30);    // Red
    colorWipe(strip.Color(  0, 255,   0), 30);    // Green
    colorWipe(strip.Color(  0,   0, 255), 30);    // Blue
    pulse(tron, 50, 0);
    rainbow(30);
  } else if (mode == 'o') {
    strip.clear();
    strip.show();
    while (checkNewMode() == false) {
      delay(100);
    }
  } else {
    while (checkNewMode() == false) {
      delay(100);
    }
  }
}

boolean checkNewMode() {
  if (bSerial.available()){
    data = bSerial.read();
    if (data != ' ' && data != mode) {
      mode = data;
      newMode = true;
    }
  }
  
  return newMode;
}

void pulse(uint32_t c, uint16_t width, uint8_t wait) {
  uint16_t widthStep = 100 / (width / 2);
  for (uint16_t i = 0; i < strip.numPixels(); i++) {
    if (checkNewMode()) {break;}
    for (uint16_t j = 0; j < (width / 2); j++) {
      if ((i + j) > strip.numPixels()) {
        strip.setPixelColor(i + j - strip.numPixels(), percent(c, (j + 1)*widthStep));
      } else {
        strip.setPixelColor(i + j, percent(c, (j + 1)*widthStep));
      }
    }
    for (uint16_t k = (width / 2); k < width; k++) {
      if ((i + k) > strip.numPixels()) {
        strip.setPixelColor(i + k - strip.numPixels(), percent(c, (width - k)*widthStep));
      } else {
        strip.setPixelColor(i + k, percent(c, (width - k)*widthStep));
      }
    }
    strip.show();
    strip.setPixelColor(i, 0); // clear trailing pixel
    delay(wait);
  }
}

uint32_t percent(uint32_t orig, uint8_t perc) {
  int red = (orig >> 16) & 255;
  int green = (orig >> 8) & 255;
  int blue = (orig) & 255;
  red = (red * perc) / 100;
  green = (green * perc) / 100;
  blue = (blue * perc) / 100;
  Serial.print(red);
  Serial.print(green);
  Serial.print(blue);
  return strip.Color(red, green, blue);
}

// Fill the dots one after the other with a color
void colorWipe(uint32_t c, uint8_t wait) {
  for(uint16_t i=0; i<strip.numPixels(); i++) {
    if (checkNewMode()) {break;}
    strip.setPixelColor(i, c);
    strip.show();
    delay(wait);
  }
}

void rainbow(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256; j++) {
    if (checkNewMode()) {break;}
    for(i=0; i<strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel((i+j) & 255));
    }
    strip.show();
    delay(wait);
  }
}

// Slightly different, this makes the rainbow equally distributed throughout
void rainbowCycle(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256*5; j++) { // 5 cycles of all colors on wheel
    if (checkNewMode()) {break;}
    for(i=0; i< strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel(((i * 256 / strip.numPixels()) + j) & 255));
    }
    strip.show();
    delay(wait);
  }
}

//Theatre-style crawling lights.
void theaterChase(uint32_t c, uint8_t wait) {
  for (int j=0; j<10; j++) {  //do 10 cycles of chasing
    for (int q=0; q < 3; q++) {
      if (checkNewMode()) {break;}
      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, c);    //turn every third pixel on
      }
      strip.show();
     
      delay(wait);
     
      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
      }
    }
  }
}

//Theatre-style crawling lights with rainbow effect
void theaterChaseRainbow(uint8_t wait) {
  for (int j=0; j < 256; j++) {     // cycle all 256 colors in the wheel
    for (int q=0; q < 3; q++) {
      if (checkNewMode()) {break;}
      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, Wheel( (i+j) % 255));    //turn every third pixel on
      }
      strip.show();
       
      delay(wait);
       
      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
      }
    }
  }
}

// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t Wheel(byte WheelPos) {
  if(WheelPos < 85) {
   return strip.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
  } else if(WheelPos < 170) {
   WheelPos -= 85;
   return strip.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  } else {
   WheelPos -= 170;
   return strip.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
}
