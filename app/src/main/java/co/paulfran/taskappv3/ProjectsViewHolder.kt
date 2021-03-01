package co.paulfran.taskappv3

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProjectsViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(R.layout.project_row, parent, false)) {
    private var projectNameTextView: TextView? = null
    private var projectCountTextView: TextView? = null

    init {
        projectNameTextView = itemView.findViewById(R.id.project_title_tv)
        projectCountTextView = itemView.findViewById(R.id.project_count_tv)
    }

    fun bind(project: Project) {
        projectNameTextView!!.text = project.name
        projectCountTextView!!.text = "${project.items.count()} items"
    }
}