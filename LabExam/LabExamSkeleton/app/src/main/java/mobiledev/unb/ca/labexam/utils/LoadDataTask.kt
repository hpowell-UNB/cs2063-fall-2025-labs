package mobiledev.unb.ca.labexam.utils

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mobiledev.unb.ca.labexam.MyAdapter
import mobiledev.unb.ca.labexam.models.EventInfo

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
            // TODO 1
            //  Show the circular progress indicator

            // TODO 2
            //  Load the data from the JSON assets file and return the list of events

            launch(Dispatchers.Main) {
                // Simulate a long-running operation
                for (i in 0..DOWNLOAD_TIME) {
                    sleep()
                    // TODO 3
                    //  Update the progress attribute of the progress bar
                }

                // TODO 4
                //  Call the updateDisplay method to update the UI with the results
            }
        }
    }

    private suspend fun sleep() {
        delay(500)
    }

    private fun updateDisplay(eventsList: ArrayList<EventInfo>) {
        recyclerView.adapter = MyAdapter(eventsList) { event ->
            // TODO 5
            //  Set the onClickListener for the TextView in the ViewHolder (holder) to
            //  create an explicit intent to launch DetailActivity from
            //  the parentActivity object passed into the class constructor
            //  NOTE:
            //    You will also need to include extra information in the intent
        }

        // TODO 6
        //  Hide the circular progress indicator

        // TODO 7
        //  Create a Toast indicating that the file has been loaded
    }

    companion object {
        private const val TAG = "LoadDataTask"
        private const val DOWNLOAD_TIME = 10 // Download time simulation
    }
}