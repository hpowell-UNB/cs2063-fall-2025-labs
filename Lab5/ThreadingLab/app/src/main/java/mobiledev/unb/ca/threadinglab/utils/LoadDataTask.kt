package mobiledev.unb.ca.threadinglab.utils

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mobiledev.unb.ca.threadinglab.MyAdapter
import mobiledev.unb.ca.threadinglab.models.Course

class LoadDataTask(private val parentActivity: AppCompatActivity) {
    private val appContext: Context = parentActivity.applicationContext
    private lateinit var recyclerView: RecyclerView
    private lateinit var circularProgressIndicator: CircularProgressIndicator

    fun setRecyclerView(recyclerView: RecyclerView): LoadDataTask {
        this.recyclerView = recyclerView
        return this
    }

    fun setCircularProgressIndicator(circularProgressIndicator: CircularProgressIndicator): LoadDataTask {
        this.circularProgressIndicator = circularProgressIndicator
        return this
    }

    fun execute() {
        MainScope().launch(Dispatchers.IO) {
            // Show the circular progress indicator
            circularProgressIndicator.visibility = ProgressBar.VISIBLE

            // TODO 1
            //  Load the data from the JSON assets file and return the list of courses

            launch(Dispatchers.Main) {
                // Simulate a long-running operation
                for (i in 0..DOWNLOAD_TIME) {
                    sleep()
                    // TODO 2
                    //  Update the progress attribute of the progress bar
                }

                // TODO 3
                //  Using the updateDisplay method update the UI with the results
            }
        }
    }

    private suspend fun sleep() {
        delay(500)
    }

    private fun updateDisplay(courseList: ArrayList<Course>) {
        recyclerView.adapter = MyAdapter(courseList) { item ->
            // This will loop through each course element and apply an action to it
            // TODO 4
            //  Set the onClickListener for the TextView in the ViewHolder (holder) to create
            //  an explicit intent to launch DetailActivity
            //  from the parentActivity object passed into the class constructor
            //  HINT: You will need to put three extra pieces of information in this intent:
            //      The Course ID
            //      The Course Name
            //      The Course Description
        }

        // Hide the circular progress indicator
        circularProgressIndicator.visibility = ProgressBar.INVISIBLE

        // TODO 5
        //  Create a Toast indicating that the file has been loaded
        //    HINT: Read this for help with Toast:
        //    http://developer.android.com/guide/topics/ui/notifiers/toasts.html
    }

    companion object {
        private const val TAG = "LoadDataTask"
        private const val DOWNLOAD_TIME = 10 // Download time simulation
    }
}