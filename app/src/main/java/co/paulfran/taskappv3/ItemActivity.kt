package co.paulfran.taskappv3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.paulfran.taskappv3.R
import co.paulfran.taskappv3.databinding.ActivityItemBinding

class ItemActivity : AppCompatActivity() {

    lateinit var binding: ActivityItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var selectedProject = intent.getIntExtra("projectIndex", 0)
        var thisProject = AppData.projects[selectedProject]

        binding.toolbarTitle.text = thisProject.name

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}