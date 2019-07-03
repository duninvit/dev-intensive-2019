package ru.skillbranch.devintensive.extensions


fun String.truncate(countTruncate: Int = 16): String {
    return when {
        countTruncate == 0 -> ""
        this.trim().length > countTruncate -> "${this.substring(0, countTruncate + 1).trim()}..."
        else -> this.trim()
    }


}

fun String.stripHtml(): String {
    return this
        .replace("<[^>]*>".toRegex(), "")
        .replace("&(.*?);".toRegex(), "")
        .replace("\\s+".toRegex(), " ")
        .trim()
}