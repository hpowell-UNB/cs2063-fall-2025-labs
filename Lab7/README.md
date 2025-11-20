# Lab 7 - Gestures and Animations

## Introduction
We have seen in class how to detect gestures, create animations, and play sounds. In this lab we'll put these pieces together to create a game-like app.  


### Getting Started

For this you have been provided with skeleton code with TODO items remaining to be completed.

* `BubbleListener.kt` 
  * Interface which defines the function that will be defined and used when a bubble is destroyed
  
* `BubbleView.kt`
  * Contains the logic used to work with individual bubble objects which appear on the screen

* `DisplayUtils.kt`
  * Contains global context screen display height and width attributes used to determine if a bubble is still on the screen

* `MainActivity.kt`
  * Entry point for the application
  * Contains the concrete BubbleListener interface implementation


### Gameplay

The game begins with a blank screen with the following actions that can be performed.

* Tapping on the screen will create a bubble at that location
  * The bubble will have a random size, rotation, and direction of movement
* Tapping on a bubble will pop it and play a popping sound
* Starting a fling gesture on a bubble will fling that bubble off the screen at a velocity determined by the fling gesture
* If left alone a bubble will eventually move off the screen
* A counter at the bottom of the screen keeps track of the number of bubbles on the screen

### Useful Links

* [SoundPool](http://developer.android.com/reference/android/media/SoundPool.html)
Also see the `SoundPool` example in the course GitHub repository.
* [MotionEvent](http://developer.android.com/reference/android/view/MotionEvent.html)
* See the `GestureDetector` examples [here](http://developer.android.com/training/gestures/detector.html) and in the course GitHub repository for how to delegate `MotionEvent`s.
* [BitMap](http://developer.android.com/reference/android/graphics/Bitmap.html) - including how to create a scaled `Bitmap`
* [Canvas](http://developer.android.com/reference/android/graphics/Canvas.html)


**Lab Todo**

Examine the code to get an understanding of what's already implemented. You don't need to understand every line, but should understand the overall structure of the app.  This lab requires you to learn independently and read lots of documentation. See the __Gameplay__ section on how to test the completed app.

Complete the TODOs in `MainActivity.kt` and `BubbleView.kt`.  Have fun!

**Lab Completion**

* LABS COMPLETED IN CLASS: 
  * Show the working app running on an emulator to the instructor or TA for signoff.
  * No files need to be submitted.
* LABS COMPLETED OUTSIDE OF CLASS: 
  * Submit `MainActivity.kt` and `BubbleView.kt` to the Lab7 drop box folder on D2L
   
* Keep a copy of your project work and answers for future reference
