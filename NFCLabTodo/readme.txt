Programming in NFC Lab

STEP ONE - Investigate
---------
Take a look at one or some of the electronic tags distributed around the room. You'll be using these to read messages from. In many real-world scenarios you'll have full documentation of the software, hardware, and all the specs of what you're working with. Sometimes you will not, and you might have to reverse engineer a bit to solve the problem. See what kind of information you can discover about the tags and what type of libraries may be able to help you program something for these. 


STEP TWO - Program
---------
Download the shell program from the github. If there are any strange version, typing, etc errors, you can just work through and try to debug these as usual.


ANDROIDMANIFEST.XML
-------------------->
There are two things missing from this: the uses-feature for NFC, and the intent for the NFC detection itself
The reading in the MainActivity documentation can help you figure out how to fill these in

MAINACTIVITY.KT
------------------>
There are six TODOs for the primary "Read an NFC tag" functionality. Fill these in first and test it out

The final (seventh) TODO is to submit the lab

SUBMIT
---------
To submit the lab, complete the seventh TODO task in MainActivity to send a message over NFC to an NFC reader and script attached to the front of the lab room. In your message, include your names for the submission and the code you retrieved from the electronic tag
