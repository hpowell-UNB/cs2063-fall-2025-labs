package mobiledev.unb.ca.roompersistencelab.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import mobiledev.unb.ca.roompersistencelab.db.AppDatabase
import mobiledev.unb.ca.roompersistencelab.entities.Item
import mobiledev.unb.ca.roompersistencelab.repositories.ItemRepository

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    // Repository reference
    private val itemRepository: ItemRepository

    // Expose the search results
    //  NOTE: This variable will be observed for change in the main activity
    val searchItems = MutableLiveData<List<Item>>()

    init {
        val itemDao = AppDatabase.getDatabase(application, viewModelScope).itemDao()
        itemRepository = ItemRepository(itemDao)
    }

    // TODO
    //  Add mapping calls between the UI and Database for inserting a record

    // TODO
    //  Add mapping call between the UI and Database for searching the records
    //  Hint:
    //    Lookup the records and use the postValue function to update the searchItems variable
    //    See https://developer.android.com/reference/androidx/lifecycle/MutableLiveData
    //    for additional information
}