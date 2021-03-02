package co.paulfran.taskappv3

class AppData {

    companion object DataHolder {

        var dbFileName = "project_db"
        lateinit var db: ProjectDatabase

        var projects: MutableList<ProjectWithItems> = mutableListOf()

        fun initialize() {

            val project1 = Projects("Invoicing App")

            val item1 = Items("Login Screen", project1.name, false)
            val item2 = Items("Add Vehicle Screen", project1.name, false)
            val item3 = Items("Add Inspection Screen", project1.name, false)

            val projectWithItems1 = ProjectWithItems(project1, mutableListOf(item1, item2, item3))

            projects = mutableListOf(projectWithItems1)
        }
    }
}