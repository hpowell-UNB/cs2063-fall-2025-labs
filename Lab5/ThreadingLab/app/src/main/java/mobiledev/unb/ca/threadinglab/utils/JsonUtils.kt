package mobiledev.unb.ca.threadinglab.utils

import android.content.Context
import mobiledev.unb.ca.threadinglab.models.Course
import org.json.JSONObject
import org.json.JSONException
import java.io.IOException
import kotlin.collections.ArrayList

class JsonUtils(context: Context) {
    // Publically accessible list of courses
    // The setter method is private and can only be accessed by this class
    var courses: ArrayList<Course> = ArrayList()
        private set

    private fun processJSON(context: Context) {
        try {
            val jsonString = loadJSONFromAssets(context)
            jsonString?.let { jsonString ->
                val jsonObject = JSONObject(jsonString.trimIndent())

                // Create a JSON Array from the JSON Object
                // This array is the "courses" array mentioned in the lab write-up
                val jsonArray = jsonObject.getJSONArray(KEY_COURSES)
                for (i in 0 until jsonArray.length()) {
                    // TODO 2
                    //  Using the JSON array set the array of courses
                    //  1. Retrieve the current JSON object from the array by index
                    //  2. Create a Course object from the JSON object
                    //  3. Add the Course object to courses ArrayList
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromAssets(context: Context): String? {
        // TODO 1
        //  1. Obtain an instance of the AssetManager class from the referenced context
        //    (https://developer.android.com/reference/android/content/Context#getAssets())
        //  2. Open the CS_JSON_FILE from the assets folder
        //     (https://developer.android.com/reference/android/content/res/AssetManager)
        //  3. Read the file contents into a string
        //  HINT:
        //   A BufferedReader (https://www.geeksforgeeks.org/read-from-files-using-bufferedreader-in-kotlin/)
        //   or InputStream (https://www.baeldung.com/kotlin/inputstream-to-string)
        //   works well in this case
        TODO("Not yet implemented")
    }

    companion object {
        private const val CS_JSON_FILE = "CS.json"
        private const val KEY_COURSES = "courses"
        private const val KEY_COURSE_ID = "courseID"
        private const val KEY_NAME = "name"
        private const val KEY_DESCRIPTION = "description"
    }

    // Initializer to read our data source (JSON file) into an array of course objects
    init {
        processJSON(context)
    }
}