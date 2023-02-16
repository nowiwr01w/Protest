package com.nowiwr01p.core.extenstion

import com.nowiwr01p.core.datastore.cities.data.Meeting

private fun Int.formatPeopleCount(): String {
    val text = when (this % 10) {
        2, 3, 4 -> if (this > 20) "человека" else "человек"
        else -> "человек"
    }
    return "$this $text"
}

fun Meeting.getPeopleGoCountAll(): String {
    return (reaction.peopleGoCount.size + reaction.peopleMaybeGoCount.size).let { definitelyGo ->
        if (definitelyGo == 0) "" else definitelyGo.formatPeopleCount()
    }
}

fun Meeting.getPeopleGoCountShort() = reaction.peopleGoCount.size.let { definitelyGo ->
    if (definitelyGo == 0) "" else definitelyGo.formatPeopleCount()
}

fun Meeting.getPeopleGoCount() = reaction.peopleGoCount.size.let { definitelyGo ->
    if (definitelyGo == 0) "" else "${definitelyGo.formatPeopleCount()} точно пойдут\n"
}

fun Meeting.getPeopleMaybeGoCount() = reaction.peopleMaybeGoCount.size.let { maybeGo ->
    if (maybeGo == 0) "" else "${maybeGo.formatPeopleCount()}, возможно, тоже\n"
}

fun String.storageUrl() = replace("/o/meetings/", "/o/meetings%2F")