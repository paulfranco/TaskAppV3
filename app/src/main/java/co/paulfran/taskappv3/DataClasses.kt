package co.paulfran.taskappv3

import androidx.room.*

@Entity
data class Items(
        @ColumnInfo(name = "item_name")
        val name: String,
        @ColumnInfo(name = "project_name")
        val projectName: String,
        var completed: Boolean
) {
        @PrimaryKey(autoGenerate = true)
        var id = 0
}

data class Projects(
        @ColumnInfo(name = "project_name")
        val name: String
) {
        @PrimaryKey(autoGenerate = true)
        var id = 0
}

data class ProjectWithItems(
        @Embedded
        val project: Projects,
        @Relation(parentColumn = "project_name", entityColumn = "project_name")
        val items: MutableList<Items>
)