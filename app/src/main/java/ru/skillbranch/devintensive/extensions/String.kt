package ru.skillbranch.devintensive.extensions


fun String.truncate(countTruncate: Int = 16): String {
    return if (this.trim().length > countTruncate)
        "${this.substring(0, countTruncate).trim()}..."
    else
        this.trim()


}

fun String.stripHtml(): String {
    return this
        .replace("<[^>]*>".toRegex(), "")
        .replace("&(.*?);".toRegex(), "")
        .replace("\\s+".toRegex(), " ")
        .trim()
}