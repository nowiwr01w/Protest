package com.nowiwr01p.core.model

import com.nowiwr01p.core.model.ArticleData.*
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val dateViewers: DateViewers = DateViewers(),
    val topImage: TopImage = TopImage(),
    val title: Title = Title(),
    val descriptions: List<Description> = listOf(),
    val imagesLists: List<ImageList> = listOf(),
    val orderedLists: List<OrderedList> = listOf()
) {
    fun buildContent() = mutableListOf(dateViewers, topImage, title).apply {
        imagesLists.forEach { add(it) }
        descriptions.forEach { add(it) }
        orderedLists.forEach { add(it) }
    }.sortedBy {
        it.order
    }

    companion object {
        val article = Article(
            dateViewers = DateViewers(
                date = 1673103472373,
                viewers = listOf("1", "2", "3", "4", "5", "6")
            ),
            topImage = TopImage(
                priority = 1,
                link = "https://meduza.io/impro/AkUHLyZq701-3BQtp471Bc69owpX3N3PUEE0RguMJPs/fill/2670/0/ce/1/aHR0cHM6Ly9tZWR1/emEuaW8vaW1hZ2Uv/YXR0YWNobWVudHMv/aW1hZ2VzLzAwOC82/NTAvNjQ0L29yaWdp/bmFsLzZoaHR0aXpk/Z0t6S1B6XzlnV3Bp/aUEuanBn.webp"
            ),
            title = Title(
                priority = 3,
                text = "В Днепре из-за ракетного удара погибли 40 человек, больше 70 пострадали. Зеленский призвал россиян перестать «трусливо молчать»"
            ),
            descriptions = listOf(
                Description(
                    priority = 4,
                    text = "По последним официальным данным, 40 человек погибли из-за российского ракетного удара по Днепру, совершенного 14 января, в результате которого обрушились два подъезда девятиэтажного дома. Об этом днем 16 января сообщил телеканал «Суспiльне», ссылаясь на Днепропетровскую областную администрацию."
                ),
                Description(
                    priority = 5,
                    text = "Утром 16 января в МВД Украины сообщили, что погибли 36 человек (в том числе двое детей), еще 75 человек пострадали (из них 15 детей). Спасли 39 человек, в том числе шестерых детей. Неизвестна судьба еще около 30 человек."
                ),
                Description(
                    priority = 6,
                    text = "В доме разрушены 72 квартиры, еще 230 получили повреждения, сообщил глава Днепропетровского облсовета Николай Лукашук. По его словам, к утру 16 января с места происшествия вывезли более 7,4 тысячи тонн разрушенных конструкций. Спасатели продолжают искать людей уже почти двое суток. К поисково-спасательной операции привлекли свыше 500 человек и 140 единиц техники."
                ),
                Description(
                    priority = 9,
                    text = "24 февраля в 17:30.\nЦентральные площади городов.\nДо встречи!!"
                )
            ),
            imagesLists = listOf(
                ImageList(
                    priority = 7,
                    images = listOf(
                        Image(
                            link = "https://meduza.io/impro/tv3AfQB-YR8oBY9Img7KqZwt7B-kQWJ6CqDOHDOk1QM/fill/1960/0/ce/1/aHR0cHM6Ly9tZWR1/emEuaW8vaW1hZ2Uv/YXR0YWNobWVudHMv/aW1hZ2VzLzAwOC82/NTAvNTU2L29yaWdp/bmFsL2lQYmJ0RzFk/QTY3ZlVfOXJrN1o0/LUEuanBn.webp",
                            description = "Спасатели ищут людей"
                        )
                    ),
                )
            ),
            orderedLists = listOf(
                OrderedList(
                    priority = 8,
                    title = "Что мы можем с этим сделать?",
                    steps = listOf(
                        "Выйти на митинг",
                        "Взять с собой кучу плакатов",
                        "Пройтись по центральным улицам",
                        "Послать чертилу нахуй",
                        "Заставить власть прекратить войну"
                    )
                )
            )
        )
    }
}