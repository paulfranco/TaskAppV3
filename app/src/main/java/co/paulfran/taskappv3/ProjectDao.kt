package co.paulfran.taskappv3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ProjectDao {

    // Insert a Project
    @Insert
    suspend fun insertProject(project: Projects)

    // Insert an item
    @Insert
    suspend fun insertItem(item: Items)

    // Get all projects and items
    @Transaction
    @Query("SELECT * FROM Projects")
    suspend fun getProjectsWithItems(): MutableList<ProjectWithItems>

    // Get items for a project
    @Query("SELECT * FROM Items WHERE project_name=:projectName")
    suspend fun getItemsOfProject(projectName: String): MutableList<Items>

    // Delete project
    @Query("DELETE FROM Projects WHERE project_name=:projectName")
    suspend fun deleteProject(projectName: String)

    // Delete item
    @Query("DELETE FROM Items WHERE project_name=:projectName AND item_name=:itemName")
    suspend fun deleteItem(projectName: String, itemName: String)

    // Delete projects and items
    @Query("DELETE FROM Items WHERE project_name=:projectName")
    suspend fun deleteItemsOfProject(projectName: String)

    // Update an item
    @Query("UPDATE Items SET completed=:completedVal WHERE item_name=:itemName AND project_name=:projectName")
    suspend fun updateItem(projectName: String, itemName: String, completedVal: Boolean)

}