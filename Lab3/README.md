# Lab 3 - Activities and Intents

Today’s lab will go deeper into Android Intents by demonstrating how to use explicit and implicit Intents between a couple of Activity classes.

#### Objectives

You are given an Android project containing skeleton Activity class files. Your task is to develop a thorough understanding of starting new activities by Intents which was introduced briefly last lab.  We will go deeper into more exploration below.

The Android API Guide succinctly states an "Intent is a messaging object you can use to request an action from another app component" and for that reason they are a very powerful tool for getting feature rich applications running very quickly. We will cover:

* Explicit and implicit Intents
* Passing information with Intents
* Application-specific Intent Filters
* Permissions
* Activity Result APIs

The goal of this lab is to create a multiple Activity application that uses multiple explicit and implicit Intents. We'll access activities from within our application as well as request access for activities outside our application.

We'll access camera functionality as well as the capability to send email using existing applications on the device. We'll also explore a potential pitfall if the behavior of the task backstack is misunderstood.

#### Pre-Lab Reading

For this lab you need to be familiar with how to build an Intent and how to construct and call new Activities:

* [Intents and Intent Filters](https://developer.android.com/guide/components/intents-filters)
* [Common Intents](http://developer.android.com/guide/components/intents-common.html)
  * This isn't necessary for pre-lab reading, however, you may find it valuable during this course and any future Android development
* You will need to use either a device or emulator for testing; additonal information can be be found in the [Debugging and Testing](../docs/DEBUGGING.md) document.

##### Prerequisites

* If you are using an emulator for this lab (e.g., on your own laptop) you'll need to turn on emulated device camera access
  * This can be found under advanced settings when creating an emulated device.
* For any device you are using to test this lab (a real phone or emulator) you will need to set up an email account on the device if one is not already set up.
  * This can be done on most Android devices via ```Settings → Accounts → Add Account```.

## Pair Programming

We will again be doing pair programming for this lab.  Details on pair programming can be found at [Pair Programming](../docs/PAIR_PROGRAMMING.md).  You can again work with anybody of your choosing.


#### Intent Types

New Activities are started by passing the ```startActivity()``` method an ```Intent``` parameter.
* Intents can be of **explicit** (stated) or **implicit** (implied) nature.
* Intents can be sent from an Activity component to start new Activities which was covered in some detail during Lab 2.
* Intents can also start Services and Broadcast Receivers (this will be covered in a later lab) as well as be started _by_ Services and Broadcast Receivers.

_Explicit Intent_
* Typically used to start a component (e.g.' ```Activity```/```Service```/```Broadcast Receiver```) that exists within your application project; something coded by you or imported for a specified purpose within your application.
* It is called explicit because you know the name of the component you intend to use, so you can explicitly announce it to the ```startActivity()``` method, and it will be handled as such.

_Implicit Intent_
* If you are calling for a component from another application installed on the device, you may not have knowledge of the component name that you are requesting; you just wish to make use of the component if it’s available on the device.
* An application can choose to specify if it contains components that satisfy certain request types by supplying ```Intent Filters``` in their ```AndroidManifest.xml``` file
* By providing such filters, their application can make certain activities available to satisfy some request from other applications. This creates even more modularity in creating application services.
* If multiple applications on the device satisfy some request (e.g., to take a photo), a list of possible choices is presented to the user; users can then choose, or not, to have their choice set as default behavior

Investigate the constructors available for building ```Intent``` objects [here](http://developer.android.com/reference/android/content/Intent.html#lfields).
* Take a look at some of the public methods available to Intents
  * Note in particular the various ```putExtra()``` methods
  * The option to attach extra information to an ```Intent``` provides a great way to send data between two Activities
  * By packing an ```Intent``` with extras, its contents can be checked for, and then unpacked, in the receiving ```Activity```
* This is similar behavior to how we attached counter values using ```savedInstanceState``` in ```onSaveInstanceState()``` from ```ActivityOne``` in Lab 2
* Instead of using ```savedInstanceState.put<<some_value_type>>()```, we instead do ```intentName.putExtra(type, value)```.

##### Reminder
* Remember to make habit of adding a ```LogCat``` documentation ```String``` to all Activities and a ```Log.i``` method to each ```Activity``` method for testing purposes!
* Take a look at the API documentation for the [Log](http://developer.android.com/reference/android/util/Log.html) class and/or ```Lab 2``` Skeleton code for ```ActivityOne```.

##### Initialize Lab Project State

1. Open Android Studio
2. Import the Lab 3 Skeleton project

Note the following information about the starting project code:
  * `MainActivity` represents the start activity.
  * It has already been setup to make an explicit intent call to the `ExternalActivityCalls` class.
    * This is similar to what you have seen in Lab 2 when we executed an explicit ```Intent``` to start ```ActivityTwo``` from the ```Context``` of ```ActivityOne```.
  * The `ExternalActivityCalls` class contains three buttons:
    * The Camera ```Button``` is going to send out an implicit ```Intent``` requesting a ```Camera``` application ```Activity``` that can handle taking photos.
    * The Email ```Button``` is going to send out an implicit ```Intent``` requesting an Email ```Activity``` with which to send email.
    * The Return ```Button``` will execute an explicit ```Intent``` requesting ```Main Activity```.
      * An interesting task backstack implications will be explored from using this approach.

For this labe we will be define the application's ```Intent``` to have some function performed on its behalf using implicit intents.

NOTES:
* When implicitly defining an ```Intent``` we need to pass along an ```Action Constant```.
  *  This tells the device how to locate applications that contain an ```Activity``` which is capable of responding to your application's request.
  * If a default application ```Activity``` has been set to satisfy that function on the device, it will generally be the ```Activity``` started.
  
**Task 1**

Let's demonstrate this by announcing our application’s ```Intent``` to send an email.  We don't need to concern ourselves with the underlying method; an email application implements to succeed at transferring our message to the recipient.  We need only to announce our intention to send email to the Android operating system and it will inform the user what options are available.

We can provide information to the ```Activity``` we intend to start. This information is sent along with the ```Intent``` as attached extras when we call ```startActivity()```with the ```Intent``` we created as a parameter.  This extra information is then usable within the component (i.e. ```Activity```/```Service```/```Broadcast Receiver```) we started.

1. Locate the ```dispatchSendEmailIntent``` function; this will be used to perform the email action.
2. Inside this function build an email-only ```Intent``` that __does not__ include attachments and is to be used by email applications only inside this function.
    * For testing give your ```Intent``` three extras:
      * One for the recipient email addresses (just have one recipient, and make it you).
      * One for the subject line “CS2063 Lab 3”.
      * One for the body of the email “This is a test email!”.
    * A list of possible Actions and Extras available for communicating with email applications on the device as well as email ```Intent``` creation can be found.
[here](http://developer.android.com/guide/components/intents-common.html#Email).
    

**Task 2**

Certain requested components may require access to device hardware; in this case the camera. If your application requests use of a component that in turn will make use of such hardware you must announce that in your application’s manifest file.

For this you will be updating the [AndroidManifest.xml](http://developer.android.com/guide/topics/manifest/manifest-intro.html) file to include the following:
  * A [uses-feature](https://developer.android.com/training/camera-deprecated/photobasics#TaskManifest) tag for the camera.
    * Specific details for using the camera can be found [here](https://developer.android.com/training/camera-deprecated/photobasics#TaskManifest).
  * A [uses-permission](https://developer.android.com/training/data-storage/files.html#GetWritePermission) tag to enable writing to external storage (on-device storage that will be used to store photos that have been taken).
    *  Specific details can be found [here](https://developer.android.com/training/camera-deprecated/photobasics#TaskPath).

**Task 3**

With the required hardware feature to be used declared for the app you can wire your camera ```Button```’s ```setOnClickListener()``` event to implicitly alert the operating system of your ```Activity```'s ```Intent``` to access to the camera functionality. Doing so will provide the user a list of applications to satisfy the request to take a photo.

1. Locate the ```dispatchTakePhotoIntent``` function; this will be used to perform the camera action.
2. Using the example code from the [Save a full-size photo](https://developer.android.com/training/camera-deprecated/photobasics#TaskPath) documentation as a base update the function to create an Intent used to take a picture and save the full-size photo.  Please note the following:
    * The variable for ```currentPhotoPath``` is already defined.
    * The file **file_paths.xml** has already been included in the project at _**app/src/main/res/xml**_.  You do not need to create it.
    * You will notice that ```startActivityForResult()``` has been deprecated.  We will look to fix this in the next task.
  
Implementation Note: Replace ```"com.example.android.fileprovider"``` with ```"mobiledev.unb.ca.lab3intents.provider"```  for the authority name in the code.


**Task 4**

With the camera intent code in place for Task 3 we will now look to replace the deprecated ```startActivityForResult()``` by using the  [Activity Result APIs](https://developer.android.com/training/basics/intents/result).  

To help with this you will notice an attribute called ```cameraActivityResultLauncher``` near the top of the file.  We will be working with this object to register a listener for photo capture events.

1. Replace the deprecated line 
```kotlin
startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
```
with
```kotlin
cameraActivityResultLauncher?.launch(takePictureIntent)
```

**Task 5**

At this point we have saved the photo and we know where it has been saved (HINT: the string defined in Task 4 as part of saving the file).  The next step is to alert Android to add this file to the photos database so that other applications (i.e. - the Gallery app) know about it.

We are now going to set the ```cameraActivityResultLauncher``` object to recieve the activity result and write the picture to the gallery.  We will be doing this by using the ```registerForActivityResult()``` function.  

1. In the class code add the following code to the ```setCameraActivityResultLauncher``` function.
```kotlin
cameraActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                galleryAddPic()
            }
        }
```
  * This will take the result of the activity intent action (in this case the action of taking a photo) and save the results to the gallery
  * Additional details can be found [here](http://developer.android.com/training/basics/intents/result.html#ReceiveResult).
  * The ```galleryAddPic()``` function contains the code used to save the picture depending on API level being used.
    * We will cover storage in a later lecture, for now the purpose of this lab is to work with intents so this code is provided.
    * You may also find the photo gets saved to the file system outside of the gallery; this is also fine for the purposes of the lab.
  

**Task 6**

With the email and camera intents in place we now have to make our back ```Button``` work by implementing an ```Intent``` to explicitly call our ```MainActivity```.
* This is done similarly to how we built and started an ```Intent``` to get from our ```MainActivity``` to ```ExternalActivityCalls``.`

**Task 7**

With the Intents implemented run the application and perform the following steps:
1. Take a photo
2. Send an email to yourself using the mail button
3. From within the email app attach the photo you just took as an attachment
4. Open the email and take a screenshot of it

**Task 8**

Restart your application and perform the following steps:
1. From the ```MainActivity``` click the Start ```Button```.
2. Click the Back button and then Start again.
3. Do this a few times.
4. Now begin using the device back button to traverse the task backstack.
5. Write a short description of your observations and relate this to what you learned about the task backstack in the previous lab.

**Task 9**

1. Modify the implementation of the ```ExternalActivityCalls```'s back button to behave similarly to the device's soft-key back button.
  * Important - comment out the original intent implication so we can mark this accordingly.
2. Retry the steps in task 8.

**Writeup Task**

1. Include details of the observations from Tasks 8 and 9.
2. Add the screenshot of the email sent by your app.

**Deliverables**

* LABS COMPLETED IN CLASS: 
  * Show the working app running on an emulator to the instructor or TA for signoff.
  * No files need to be submitted.
* LABS COMPLETED OUTSIDE OF CLASS: 
  * Submit `MainActivity.kt`, `ExternalActivityCalls.kt`, `AndroidManifest.xml`, and your answers to the writeup task to the Lab3 drop box folder on D2L
 
* Keep a copy of your project work and answers for future reference
