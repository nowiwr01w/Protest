package com.nowiwr01p.core.extenstion

import com.nowiwr01p.core.datastore.cities.data.Meeting

private fun Int.formatPeopleCount(): String {
    val text = when (this % 10) {
        2, 3, 4 -> "человека"
        else -> "человек"
    }
    return "$this $text"
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