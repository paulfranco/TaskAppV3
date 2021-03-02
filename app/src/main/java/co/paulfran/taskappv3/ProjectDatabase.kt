package co.paulfran.taskappv3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Items::class, Projects::class], version = 1)
abstract class ProjectDatabase: RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    companion object {
        var instance : ProjectDatabase? = null

        fun getDatabase(context: Context): ProjectDatabase? {
            if (instance == null) {
                synchronized(ProjectDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext, ProjectDatabase::class.java, "temporary_name").build()
                }
            }
            return instance
        }
    }
}