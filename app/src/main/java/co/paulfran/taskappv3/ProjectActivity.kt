package co.paulfran.taskappv3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.paulfran.taskappv3.databinding.ActivityProjectBinding
import java.io.File
import java.lang.Exception

class ProjectActivity : AppCompatActivity(), OnProjectClickListener {

    private lateinit var binding: ActivityProjectBinding
    private var projectsAdapter: ProjectsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.projectRecyclerView.layoutManager = LinearLayoutManager(this)

        projectsAdapter = ProjectsAdapter(AppData.projects, this)
        binding.projectRecyclerView.adapter = projectsAdapter

        if (databaseFileExists()) {
            // Read the content from Room

        } else {
            // the very first time we are opening the app
            AppData.initialize()

            // show content in adapter
            projectsAdapter = ProjectsAdapter(AppData.projects, this)
            binding.projectRecyclerView.adapter = projectsAdapter

            // now save the info to Room
        }
    }

    override fun onResume() {
        super.onResume()
        projectsAdapter!!.notifyDataSetChanged()
    }

    fun createNewProject(v: View) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("New Project")
        builder.setMessage("Please enter a name for your new project")

        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("Save") { dialog, which ->
            val projectName: String = input.text.toString()
            val newProject = Project(projectName, mutableListOf())

            AppData.projects.add(newProject)
            // Inserted at last position
            projectsAdapter!!.notifyItemInserted(AppData.projects.count())

        }

        builder.setNegativeButton("Cancel") { dialog, which ->

        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun projectClick(index: Int) {
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra("projectIndex", index)
        startActivity(intent)
    }

    override fun projectLongClick(index: Int) {
        AppData.projects.removeAt(index)
        projectsAdapter!!.notifyItemRemoved(index)
    }

    private fun databaseFileExists(): Boolean {
        return try {
            File(getDatabasePath(AppData.dbFileName).absolutePath).exists()
        } catch (e: Exception) {
            false
        }
    }
}



