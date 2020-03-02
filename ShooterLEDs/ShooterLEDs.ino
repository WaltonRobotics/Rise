#include <FastLED.h>

#define NUM_LEDS 60
#define LED_PIN 5
#define FROM_RIO_ONE 8
#define FROM_RIO_TWO 9

CRGB leds[NUM_LEDS];
int gradient_hues[NUM_LEDS];

int turnLeftIndex = NUM_LEDS / 2 - 1;   // Moving from 29 -> 0
int turnRightIndex = NUM_LEDS / 2;  // Moving from 30 -> 59
int turnFadeStep = map(1, 0, NUM_LEDS, 0, 255);

void setup() {
  FastLED.addLeds<WS2812B, LED_PIN> (leds, NUM_LEDS);
  pinMode(FROM_RIO_ONE, INPUT);
  pinMode(FROM_RIO_TWO, INPUT);

  initGradient();
}

void loop() {
  if(digitalRead(FROM_RIO_ONE) && digitalRead(FROM_RIO_TWO)) {
    showFoundTarget();
  } else if(digitalRead(FROM_RIO_ONE)) {
    showTurnLeft();
  } else if(digitalRead(FROM_RIO_TWO)) {
    showTurnRight();
  } else {
    showGradient();
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
    gradient_hues[i] = (gradient_hues[i] + 1) % 255;
    leds[i] = CHSV(gradient_hues[i], 255, 255);
    FastLED.show();
  }
}

void showTurnLeft() {
  turnLeftIndex = turnLeftIndex - 1;
  if(turnLeftIndex < 0) {
    turnLeftIndex = NUM_LEDS / 2 - 1;
  }
  for(int i = 0; i < NUM_LEDS / 2; i++) {
    if(i = turnLeftIndex) {
      leds[i] = CRGB::Blue;
    } else {
      leds[i].b = max(0, leds[i].b - turnFadeStep);
    }
  }
  for(int i = NUM_LEDS / 2; i < NUM_LEDS; i++) {
    leds[i] = CRGB::Black;
  }
  FastLED.show();
}

void showTurnRight() {
  turnRightIndex = turnRightIndex + 1;
  if(turnLeftIndex >= NUM_LEDS) {
    turnLeftIndex = NUM_LEDS / 2;
  }
  for(int i = NUM_LEDS / 2; i < NUM_LEDS; i++) {
    if(i = turnRightIndex) {
      leds[i] = CRGB::Blue;
    } else {
      leds[i].b = max(0, leds[i].b - turnFadeStep);
    }
  }
  for(int i = 0; i < NUM_LEDS / 2; i++) {
    leds[i] = CRGB::Black;
  }
  FastLED.show();
}

void showFoundTarget() {
  for(int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB::Green;
  }
  FastLED.show();
}
