package ru.skillbranch.devintensive.extensions


fun String.truncate(countTruncate: Int = 16): String {
    return "${this.substring(0, countTruncate).trim()}..."
}

fun String.stripHtml(): String {
    return this.replace("<[^>]*>".toRegex(), "").replace("\\s+".toRegex(), " ").trim()
}