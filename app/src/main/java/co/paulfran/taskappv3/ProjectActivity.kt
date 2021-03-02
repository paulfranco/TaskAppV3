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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        AppData.db = ProjectDatabase.getDatabase(this)!!

        if (databaseFileExists()) {
            // Read the content from Room
            CoroutineScope(Dispatchers.IO).launch {
                AppData.projects = AppData.db.projectDao().getProjectsWithItems()

                // change context to show data in recycler view
                withContext(Dispatchers.Main) {
                    // Recreate recycler view
                    projectsAdapter = ProjectsAdapter(AppData.projects, this@ProjectActivity)
                    binding.projectRecyclerView.adapter = projectsAdapter
                }
            }


        } else {
            // the very first time we are opening the app. get initial data
            AppData.initialize()

            // Recreate recycler view
            projectsAdapter = ProjectsAdapter(AppData.projects, this)
            binding.projectRecyclerView.adapter = projectsAdapter

            // now save the info to Room
            CoroutineScope(Dispatchers.IO).launch {
                // For Each ProjectWithItems
                for (projectWithItems in AppData.projects) {
                    // Insert Project
                    AppData.db.projectDao().insertProject(projectWithItems.project)
                    // for each projectWithItems
                    for (item in projectWithItems.items) {
                        // Insert item
                        AppData.db.projectDao().insertItem(item)
                    }
                }
            }

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
            val newProject = Projects(projectName)
            val newProjectWithItems = ProjectWithItems(newProject, mutableListOf())

            AppData.projects.add(newProjectWithItems)

            // Inserted at last position
            projectsAdapter!!.notifyItemInserted(AppData.projects.count())

            // Add New Project to Room
            CoroutineScope(Dispatchers.IO).launch {
                AppData.db.projectDao().insertProject(newProject)
            }


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



