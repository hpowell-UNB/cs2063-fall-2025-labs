package mobiledev.unb.ca.roompersistencelab.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mobiledev.unb.ca.roompersistencelab.R
import mobiledev.unb.ca.roompersistencelab.entities.Item

class ItemsAdapter(private val dbItems: List<Item>) :
    RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ItemsViewHolder,
        position: Int
    ) {
        val item = dbItems[position]

        // TODO
        //  Set the text used by nameTextView and numberTextView using the item object
    }

    override fun getItemCount(): Int {
        return dbItems.size
    }

    // Inner ViewHolder Class
    class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.itemText)
        val numberTextView: TextView = itemView.findViewById(R.id.numberText)
    }
}