package co.paulfran.taskappv3

class AppData {

    companion object DataHolder {
        var projects: MutableList<Project> = mutableListOf()

        fun initialize() {

            val item1 = Item("Login Screen", false)
            val item2 = Item("Add Vehicle Screen", false)
            val item3 = Item("Add Inspection Screen", false)

            val project1 = Project("Pre-Trip Inspection App", mutableListOf(item1, item2, item3))

            val item4 = Item("Login Screen", false)
            val item5 = Item("Add Invoice Screen", false)
            val item6 = Item("Invoice Details Screen", false)

            val project2 = Project("Invoicing App", mutableListOf(item4, item5, item6))

            projects = mutableListOf(project1)
        }
    }
}