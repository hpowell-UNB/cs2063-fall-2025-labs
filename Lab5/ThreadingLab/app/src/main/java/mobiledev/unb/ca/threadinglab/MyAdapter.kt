package mobiledev.unb.ca.threadinglab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mobiledev.unb.ca.threadinglab.models.Course

/**
 * Class used to populate each row of the RecyclerView
 * @param courseList The list of courses to be displayed
 * @param listener The onClickListener behaviour for each row
 */
class MyAdapter(private val courseList: List<Course>, private val listener: (Course) -> Unit) :
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
        // Get the Course at index position in courseList
        val course = courseList[position]

        // TODO 2
        //  Set the text for TextViews in MyViewHolder to use the
        //  id and name of the course

        holder.itemView.setOnClickListener { listener(course) }
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    // Inner ViewHolder Class
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseIdTextView: TextView = itemView.findViewById(R.id.courseId)
        val courseNameTextView: TextView = itemView.findViewById(R.id.courseName)
    }
}