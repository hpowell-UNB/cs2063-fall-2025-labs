package mobiledev.unb.ca.labexam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mobiledev.unb.ca.labexam.models.EventInfo

class MyAdapter(private val eventsList: List<EventInfo>, private val listener: (EventInfo) -> Unit) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        //  Get the item at index position in dataSet
        val event = eventsList[position]

        // TODO 1
        //  Set the TextViews in MyViewHolder to be the title and host city of the event

        holder.itemView.setOnClickListener { listener(event) }

        // TODO: SharedPreferences
        //  Set the CheckBox in the ViewHolder (holder) to be checked if the
        //  value stored in SharedPreferencesManager for the number of this
        //  EventInfo is true; unchecked otherwise.
        //  If there is no value in the shared preferences then the checkbox should not
        //  be checked (i.e., assume a default value of false for anything not in
        //  the shared preferences).

        // Hints:
        // https://developer.android.com/reference/android/content/SharedPreferences.html#getBoolean(java.lang.String,%20boolean)
        // https://developer.android.com/reference/android/widget/CheckBox.html
        // https://developer.android.com/reference/android/widget/CompoundButton.html#setChecked(boolean)

        // This method is called when a CheckBox is clicked, and its status
        // changes from checked to not checked, or from not checked to checked.
        // isChecked will be true if the CheckBox is now checked, and false if
        // the CheckBox is now not checked.
        holder.visitedCheckbox.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            // TODO: SharedPreferences
            //   Save the CheckBox selected state using SharedPreferencesManager
        }
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    // Inner ViewHolder Class
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventNumberTextView: TextView = itemView.findViewById(R.id.eventNumber)
        val eventHostTextView: TextView = itemView.findViewById(R.id.eventHost)
        val visitedCheckbox: CheckBox = itemView.findViewById(R.id.checkBox)
    }
}