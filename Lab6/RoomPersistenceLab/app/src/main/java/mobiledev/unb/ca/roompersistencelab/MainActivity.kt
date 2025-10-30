package mobiledev.unb.ca.roompersistencelab

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import mobiledev.unb.ca.roompersistencelab.entities.Item
import mobiledev.unb.ca.roompersistencelab.ui.ItemViewModel
import mobiledev.unb.ca.roompersistencelab.ui.ItemsAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var searchEditText: EditText
    private lateinit var resultsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(findViewById(R.id.toolbar))

        // Set the RecyclerView
        recyclerView = findViewById(R.id.recyclerView)

        // Set the ViewModel
        itemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]

        // Setup the search item LiveData observer
        itemViewModel.searchItems.observe(this) { items ->
            items?.let {
                // TODO
                //  Update the results section.
                //  If there are no results, set the results TextView to indicate that there are no results.
                //  If there are results, set the results TextView to indicate that there are results.
                //  Again, you might need to write a bit of extra code here or elsewhere, to get the UI to behave nicely.
                //  HINT:
                //    A helper function updateListView should help display the results
            }
        }

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            // TODO
            //  Check if some text has been entered in both the item and number EditTexts.
            //  If not display a toast indicating that the data entered was incomplete.
            //  HINT:
            //    Have a look at the TextUtils class (https://developer.android.com/reference/android/text/TextUtils)

            // TODO
            //  Call the addItem method using the the text from these EditTexts.
        }

        // Set the references for the views defined in the layout files
        itemEditText = findViewById(R.id.itemEditText)
        numberEditText = findViewById(R.id.numberEditText)
        resultsTextView = findViewById(R.id.resultsTextView)

        // Set the listener activity fpr the search field
        searchEditText = findViewById(R.id.searchEditText)
        searchEditText.setOnEditorActionListener { searchText, actionId, _  ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // TODO
                //  searchText is the search EditText. (EditText is a subclass of TextView.)
                //  Get the text from this view.
                //  Call the local searchRecordsByName method using the item name.
            }
            false
        }
    }

    private fun addItem(item: String, num: String) {
        // TODO
        //  Make a call to the view model to create a record in the database table

        // TODO
        //  You will need to write a bit of extra code to get the
        //  UI to behave nicely, e.g., showing and hiding the keyboard
        //  at the right time, clearing text fields appropriately.
        //  Some of that code will likely go here, but you might also make
        //  changes elsewhere in the app. Exactly how you make the
        //  UI behave is up to you, but you should make reasonable
        //  choices.
        //  HINT:
        //    There is a utility object called KeyboardUtils which may be helpful here

        showToast(getString(R.string.msg_record_added))
    }

    private fun searchRecordsByName(name: String) {
        // TODO
        //  Make a call to the view model to search for records in the database that match the query item.
        //  This will update the searchItems variable in the view model which
        //  will trigger an update to the observer defined above
    }

    private fun showToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    // Helper function used to reset the search results
    private fun clearResultsListView() {
        resultsTextView.text = ""
        updateListView(ArrayList())
    }

    private fun updateListView(items: List<Item>) {
        recyclerView.adapter = ItemsAdapter(items)
    }
}