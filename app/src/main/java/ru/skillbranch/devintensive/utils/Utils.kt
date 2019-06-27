package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts = if (!fullName.isNullOrBlank())
            fullName.deleteAllWhitespacesAroundString().split(" ") else null
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return firstName to lastName
    }
}

fun String.deleteAllWhitespacesAroundString(): String = this.replace("\\s+".toRegex(), " ").trim()
