package ru.skillbranch.devintensive.utils

object Utils {
    private val transliterationDictionary: Map<Char, String> = mutableMapOf(
        Pair('а', "a"),
        Pair('б', "b"),
        Pair('в', "v"),
        Pair('г', "g"),
        Pair('д', "d"),
        Pair('е', "e"),
        Pair('ё', "e"),
        Pair('ж', "zh"),
        Pair('з', "z"),
        Pair('и', "i"),
        Pair('й', "i"),
        Pair('к', "k"),
        Pair('л', "l"),
        Pair('м', "m"),
        Pair('н', "n"),
        Pair('о', "o"),
        Pair('п', "p"),
        Pair('р', "r"),
        Pair('с', "s"),
        Pair('т', "t"),
        Pair('у', "u"),
        Pair('ф', "f"),
        Pair('х', "h"),
        Pair('ц', "c"),
        Pair('ч', "ch"),
        Pair('ш', "sh"),
        Pair('щ', "sh'"),
        Pair('ъ', ""),
        Pair('ы', "i"),
        Pair('ь', ""),
        Pair('э', "e"),
        Pair('ю', "yu"),
        Pair('я', "ya")
    )

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts = if (!fullName.isNullOrBlank())
            fullName.deleteAllWhitespacesAroundString().split(" ") else null
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) return null
        val fistNameInitial = if (!firstName.isNullOrBlank()) firstName.take(1) else ""
        val lastNameInitial = if (!lastName.isNullOrBlank()) lastName.take(1) else ""
        return "$fistNameInitial$lastNameInitial".toUpperCase()
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var result = ""
        payload.forEach {
            result = when {
                it.isWhitespace() -> "$result$divider"
                transliterationDictionary[it.toLowerCase()].isNullOrEmpty() -> "$result$it"
                it.isUpperCase() -> "$result${transliterationDictionary[it.toLowerCase()]?.capitalize()}"
                else -> "$result${transliterationDictionary[it]}"
            }
        }
        return result
    }
}

fun String.deleteAllWhitespacesAroundString(): String = this.replace("\\s+".toRegex(), " ").trim()
