package co.paulfran.taskappv3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProjectsAdapter(private val list: List<ProjectWithItems>, listenerContext: OnProjectClickListener): RecyclerView.Adapter<ProjectsViewHolder>() {

    private var clickInterface: OnProjectClickListener = listenerContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProjectsViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        val projectWithItems = list[position]
        holder.bind(projectWithItems)

        holder.itemView.setOnClickListener {
            clickInterface.projectClick(position)
        }

        holder.itemView.setOnLongClickListener {
            clickInterface.projectLongClick(position)
            true
        }
    }

    override fun getItemCount(): Int = list.size

}