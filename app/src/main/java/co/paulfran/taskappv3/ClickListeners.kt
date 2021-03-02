package co.paulfran.taskappv3

interface OnProjectClickListener {
    fun projectClick(index: Int)
    fun projectLongClick(index: Int)
}

interface OnItemClickListener {
    fun itemClick(index: Int)
    fun itemLongClick(index: Int)
}