#include <FastLED.h>

#define NUM_LEDS 60
#define LED_PIN 5
#define FROM_RIO_ONE 8
#define FROM_RIO_TWO 9

CRGB leds[NUM_LEDS];
int gradient_hues[NUM_LEDS];

int turnLeftIndex = NUM_LEDS / 2 - 1;   // Moving from 29 -> 0
int turnRightIndex = NUM_LEDS / 2;  // Moving from 30 -> 59
int turnFadeStep = map(10, 0, NUM_LEDS, 0, 255);
int wholeFadeStep = map(5, 0, NUM_LEDS, 0, 255);
int currentColor = 0;       // 0 - Red, 1 - White, 2 - Blue

void setup() {
//  Serial.begin(9800);
  
  FastLED.addLeds<WS2812B, LED_PIN, GRB> (leds, NUM_LEDS);
  pinMode(FROM_RIO_ONE, INPUT);
  pinMode(FROM_RIO_TWO, INPUT);

  initGradient();
}

void loop() {
  if(digitalRead(FROM_RIO_ONE) && digitalRead(FROM_RIO_TWO)) {
//    showLeftAndRight();
    flashWhite();
  } else if(digitalRead(FROM_RIO_ONE)) {
    showTurnLeft();
  } else if(digitalRead(FROM_RIO_TWO)) {
    showTurnRight();
  } else {
//    flashWhite();
//    showGradient();
//    showFoundTarget();
//    showTurnLeft();
//    showTurnRight();
    showLeftAndRight();
  }
}

void initGradient() {
  for(int i = 0; i < NUM_LEDS; i++) {
    gradient_hues[i] = map(i, 0, NUM_LEDS, 0, 255);
    leds[i] = CHSV(gradient_hues[i], 255, 255);
    delay(20);
    FastLED.show();
  }
}

void showGradient() {
  for(int i = 0; i < NUM_LEDS; i++) {
    gradient_hues[i] = gradient_hues[i] + 1;
    if(gradient_hues[i] > 255) {
      gradient_hues[i] = 0;
    }
    leds[i] = CHSV(gradient_hues[i], 255, 255);
  }
  FastLED.show();
}

void showTurnLeft() {
  for(int i = NUM_LEDS / 2; i < NUM_LEDS; i++) {
    leds[i] = CRGB::Black;
  }
  turnLeftIndex = turnLeftIndex - 1;
  if(turnLeftIndex < 0) {
    turnLeftIndex = NUM_LEDS / 2 - 1;
  }
  for(int i = 0; i < NUM_LEDS / 2; i++) {
    if(i == turnLeftIndex) {
      leds[i] = CRGB::Blue;
    } else {
      leds[i].b = max(0, leds[i].b - turnFadeStep);
    }
    delay(1);
  }
  FastLED.show();
}

void showTurnRight() {
  for(int i = 0; i < NUM_LEDS / 2; i++) {
    leds[i] = CRGB::Black;
  }
  turnRightIndex = turnRightIndex + 1;
  if(turnRightIndex >= NUM_LEDS) {
    turnRightIndex = NUM_LEDS / 2;
  }
  for(int i = NUM_LEDS / 2; i < NUM_LEDS; i++) {
    if(i == turnRightIndex) {
      leds[i] = CRGB::Blue;
    } else {
      leds[i].b = max(0, leds[i].b - turnFadeStep);
    }
    delay(1);
  }
  FastLED.show();
}

void showFoundTarget() {
  for(int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB::Green;
  }
  FastLED.show();
}

void flashWhite() {
  for(int i = 0; i < NUM_LEDS; i++) {
//    if(currentColor == 0) {
//      leds[i] = CRGB(255, 0, 0);
//    } else if(currentColor == 1) {
//      leds[i] = CRGB(255, 255, 255);
//    } else if(currentColor == 2) {
//      leds[i] = CRGB(0, 0, 255);
//    }
    if(currentColor == 0) {
      leds[i] = CRGB::White;
    } else {
      leds[i] = CRGB::Black;
    }
  }
  currentColor = (currentColor + 1) % 2;
//  Serial.println(currentColor);
  FastLED.show();
  delay(300);
}

void showLeftAndRight() {
  turnLeftIndex = turnLeftIndex - 1;
  if(turnLeftIndex < 0) {
    turnLeftIndex = NUM_LEDS / 2 - 1;
  }
  for(int i = 0; i < NUM_LEDS / 2; i++) {
    if(i == turnLeftIndex) {
      leds[i] = CRGB::Green;
    } else {
      leds[i].g = max(0, leds[i].g - wholeFadeStep);
    }
    delay(1);
  }
  
  turnRightIndex = turnRightIndex + 1;
  if(turnRightIndex >= NUM_LEDS) {
    turnRightIndex = NUM_LEDS / 2;
  }
  for(int i = NUM_LEDS / 2; i < NUM_LEDS; i++) {
    if(i == turnRightIndex) {
      leds[i] = CRGB::Green;
    } else {
      leds[i].g = max(0, leds[i].g - wholeFadeStep);
    }
    delay(1);
  }
  FastLED.show();
}
