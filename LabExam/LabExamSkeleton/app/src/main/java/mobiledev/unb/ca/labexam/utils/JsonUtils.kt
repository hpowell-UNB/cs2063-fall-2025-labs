package mobiledev.unb.ca.labexam.utils

import android.content.Context
import mobiledev.unb.ca.labexam.models.EventInfo
import org.json.JSONObject
import org.json.JSONException
import java.io.IOException
import kotlin.collections.ArrayList

class JsonUtils(context: Context) {
    var eventsList: ArrayList<EventInfo> = ArrayList()
        private set

    private fun processJSON(context: Context) {
        try {
            val jsonString = loadJSONFromAssets(context)
            jsonString?.let { jsonString ->
                val jsonObject = JSONObject(jsonString.trimIndent())

                // Create a JSON Array from the JSON Object
                val jsonArray = jsonObject.getJSONArray(KEY_HOST_NATIONS)
                for (i in 0 until jsonArray.length()) {
                    // Create a JSON Object from individual JSON Array element
                    val elementObject = jsonArray.getJSONObject(i)

                    // Get data from individual JSON Object
                    val hostCity = EventInfo(
                        year = elementObject.getString(KEY_YEAR),
                        number = elementObject.getString(KEY_NUMBER),
                        hostCity = elementObject.getString(KEY_HOST_CITY),
                        dates = elementObject.getString(KEY_DATES),
                        wikipediaLink = elementObject.getString(KEY_WIKIPEDIA_LINK)
                    )

                    // Add new EventInfo object to eventsList
                    eventsList.add(hostCity)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromAssets(context: Context): String? {
        return try {
            context.assets.open(INPUT_JSON_FILE)
                .bufferedReader()
                .use { it.readText() }
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    companion object {
        private const val INPUT_JSON_FILE = "winter_games.json"
        private const val KEY_HOST_NATIONS = "host_cities"
        private const val KEY_YEAR = "year"
        private const val KEY_NUMBER = "number"
        private const val KEY_HOST_CITY = "host_city"
        private const val KEY_DATES = "dates"
        private const val KEY_WIKIPEDIA_LINK = "wikipedia_link"
    }

    // Initializer to read our data source (JSON file) into an array objects
    init {
        processJSON(context)
    }
}
