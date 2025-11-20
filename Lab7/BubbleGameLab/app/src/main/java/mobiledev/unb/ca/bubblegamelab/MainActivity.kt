package mobiledev.unb.ca.bubblegamelab

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), BubbleListener {
    // The Main view
    private lateinit var gameScreen: RelativeLayout

    // A TextView to hold the current number of bubbles
    private lateinit var bubbleCountTextView: TextView

    // Gesture Detector
    private lateinit var gestureDetector: GestureDetector

    // SoundPool attributes
    private var soundPool: SoundPool? = null
    private var poppingSoundId = 0  // ID for the bubble popping sound
    private var streamVolume = 0f // Audio volume

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Ensure app is running in dark mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        gameScreen = findViewById(R.id.gameScreen)
        bubbleCountTextView = findViewById(R.id.bubblesText)

        // Initialize the number of bubbles
        updateNumBubblesTextView()
    }

    // Method used to update the text view with the number of in view bubbles
    private fun updateNumBubblesTextView() {
        val text = getString(R.string.number_of_bubbles, gameScreen.childCount)
        bubbleCountTextView.text = text
    }

    override fun onPause() {
        super.onPause()

        // TODO
        //  Release all SoundPool resources (including sounds)
    }

    override fun onResume() {
        super.onResume()

        initSound(applicationContext)

        // Set a SoundPool OnLoadCompletedListener that calls setupGestureDetector()
        soundPool?.setOnLoadCompleteListener { _: SoundPool?, _: Int, status: Int ->
            // TODO
            //   If the status returns 0 call setGestureDetector
            //   Else log a message that the sound could not be loaded and finish
        }
    }

    // Implementation of the listener interface function
    override fun onBubbleViewRemoved(bubbleView: BubbleView, wasPopped: Boolean) {
        // Call the function to be reached through the view
        removeBubbleView(bubbleView, wasPopped)
    }

    // Update the display by removing the specified bubble view
    private fun removeBubbleView(bubbleView: BubbleView, wasPopped: Boolean = false) {
        // This work will be performed on the UI Thread
        lifecycleScope.launch {
            // TODO
            //  Remove the BubbleView from the game screen

            // TODO
            //  Update the TextView displaying the number of bubbles

            // TODO
            //  If the bubble was popped by user play the popping sound
            //  HINT: Use the streamVolume for left and right volume parameters
        }
    }

    private fun initSound(context: Context) {
        createNewSoundPool()
        setStreamVolume(context)

        // TODO
        //  Load the sound from R.raw.bubble_pop with a priority of 1
        //  Store the value in poppingSoundId
    }

    private fun createNewSoundPool() {
        // TODO
        //  Make a new sound pool allowing up to SOUND_POOL_MAX_STREAMS streams
        //  Store the object value in soundPool
    }

    private fun setStreamVolume(context: Context) {
        // AudioManager audio settings for adjusting the volume
        val audioManager = context.getSystemService(AUDIO_SERVICE) as AudioManager

        // Get the current volume Index of particular stream type
        // Stream type is set to use AudioManager.STREAM_MUSIC
        val currentVolumeIndex = audioManager.getStreamVolume(STREAM_TYPE)
            .toFloat()

        // Get the maximum volume index for a particular stream type
        val maxVolumeIndex = audioManager.getStreamMaxVolume(STREAM_TYPE)
            .toFloat()

        // Set the volume between 0 --> 1
        streamVolume = currentVolumeIndex / maxVolumeIndex
    }

    // Set up GestureDetector
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private fun setupGestureDetector() {
        gestureDetector = GestureDetector(this,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDown(motionEvent: MotionEvent): Boolean {
                    return true
                }

                // If a fling gesture starts on a BubbleView change the
                // BubbleView's velocity based on x and y velocity from
                // this gesture
                override fun onFling(
                    event1: MotionEvent?, event2: MotionEvent,
                    velocityX: Float, velocityY: Float,
                ): Boolean {
                    // TODO
                    //  Implement onFling actions (See comment above for expected behaviour)
                    //  HINT
                    //    You can get all Views from the gameScreen object at a time using
                    //    childCount method and get individual Views using
                    //    the getChildAt() method

                    return true
                }

                // If a single tap intersects a BubbleView stop the BubbleView movement
                // and "pop" the BubbleView. (Use removeBubbleView() to do this.)
                // Otherwise create a new BubbleView at the tap's location and add
                // it to gameScreen.
                //   - Don't forget to set the BubbleView listener and to start
                //     the movement of the BubbleView.
                //   - Also update the number of bubbles displayed in the
                //     appropriate TextView
                override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
                    // TODO - Implement onSingleTapConfirmed actions.
                    //  (See comment above for expected behaviour.)
                    //  HINTS
                    //    - You can get all Views from the gameScreen object at a time using
                    //      childCount method and get individual Views using
                    //      the getChildAt() function

                    return true
                }
            })
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val STREAM_TYPE = AudioManager.STREAM_MUSIC
        private const val SOUND_POOL_MAX_STREAMS = 10
    }
}