package co.paulfran.taskappv3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import co.paulfran.taskappv3.R
import co.paulfran.taskappv3.databinding.ActivityItemBinding

class ItemActivity : AppCompatActivity(), OnItemClickListener {

    lateinit var binding: ActivityItemBinding
    lateinit var thisProject: Project
    var itemsAdapter: ItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var selectedProject = intent.getIntExtra("projectIndex", 0)
        thisProject = AppData.projects[selectedProject]

        binding.toolbarTitle.text = thisProject.name

        // RecyclerView
        binding.itemsRecyclerView.layoutManager = LinearLayoutManager(this)
        itemsAdapter = ItemsAdapter(thisProject, this)
        binding.itemsRecyclerView.adapter = itemsAdapter

        // Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun itemClick(index: Int) {
        thisProject.items[index].completed = !thisProject.items[index].completed
        itemsAdapter!!.notifyDataSetChanged()
    }

    override fun itemLongClick(index: Int) {
        TODO("Not yet implemented")
    }
}