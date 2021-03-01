package co.paulfran.taskappv3

data class Item(
        val name: String,
        var completed: Boolean
)

data class Project(
        val name: String,
        var items: MutableList<Item>
)