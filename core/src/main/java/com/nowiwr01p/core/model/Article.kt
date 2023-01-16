package com.nowiwr01p.core.model

import com.nowiwr01p.core.model.ArticleContentType.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentItem(
    @SerialName("order")
    val order: Int = 0,
    @SerialName("type")
    val type: String = "",
    @SerialName("value")
    val value: String = ""
) {
    val articleType: ArticleContentType
        get() = ArticleContentType.findByType(type)
}

@Serializable
data class
Article(
    @SerialName("date")
    val date: Long = 0,
    @SerialName("content")
    val content: List<ContentItem> = listOf()
) {
    fun getField(type: ArticleContentType) = content.find { it.articleType == type }?.value ?: ""

    companion object {
        val article = Article(
            date = 1673103472373,
            content = listOf(
                ContentItem(
                    order = 1,
                    type = Image.type,
                    value = "https://meduza.io/impro/AkUHLyZq701-3BQtp471Bc69owpX3N3PUEE0RguMJPs/fill/2670/0/ce/1/aHR0cHM6Ly9tZWR1/emEuaW8vaW1hZ2Uv/YXR0YWNobWVudHMv/aW1hZ2VzLzAwOC82/NTAvNjQ0L29yaWdp/bmFsLzZoaHR0aXpk/Z0t6S1B6XzlnV3Bp/aUEuanBn.webp"
                ),
                ContentItem(
                    order = 2,
                    type = Date.type,
                    value = "1673698311454"
                ),
                ContentItem(
                    order = 3,
                    type = Title.type,
                    value = "В Днепре из-за ракетного удара погибли 40 человек, больше 70 пострадали. Зеленский призвал россиян перестать «трусливо молчать»"
                ),
                ContentItem(
                    order = 4,
                    type = Description.type,
                    value = "По последним официальным данным, 40 человек погибли из-за российского ракетного удара по Днепру, совершенного 14 января, в результате которого обрушились два подъезда девятиэтажного дома. Об этом днем 16 января сообщил телеканал «Суспiльне», ссылаясь на Днепропетровскую областную администрацию."
                ),
                ContentItem(
                    order = 5,
                    type = Description.type,
                    value = "Утром 16 января в МВД Украины сообщили, что погибли 36 человек (в том числе двое детей), еще 75 человек пострадали (из них 15 детей). Спасли 39 человек, в том числе шестерых детей. Неизвестна судьба еще около 30 человек."
                ),
                ContentItem(
                    order = 6,
                    type = Description.type,
                    value = "В доме разрушены 72 квартиры, еще 230 получили повреждения, сообщил глава Днепропетровского облсовета Николай Лукашук. По его словам, к утру 16 января с места происшествия вывезли более 7,4 тысячи тонн разрушенных конструкций. Спасатели продолжают искать людей уже почти двое суток. К поисково-спасательной операции привлекли свыше 500 человек и 140 единиц техники."
                ),
                ContentItem(
                    order = 7,
                    type = Image.type,
                    value = "https://meduza.io/impro/tv3AfQB-YR8oBY9Img7KqZwt7B-kQWJ6CqDOHDOk1QM/fill/1960/0/ce/1/aHR0cHM6Ly9tZWR1/emEuaW8vaW1hZ2Uv/YXR0YWNobWVudHMv/aW1hZ2VzLzAwOC82/NTAvNTU2L29yaWdp/bmFsL2lQYmJ0RzFk/QTY3ZlVfOXJrN1o0/LUEuanBn.webp"
                ),
                ContentItem(
                    order = 8,
                    type = ImageDescription.type,
                    value = "Спасатели ищут людей"
                ),
                ContentItem(
                    order = 9,
                    type = OrderedListTitle.type,
                    value = "Что мы можем с этим сделать?"
                ),
                ContentItem(
                    order = 10,
                    type = Step.type,
                    value = "Выйти на митинг"
                ),
                ContentItem(
                    order = 11,
                    type = Step.type,
                    value = "Взять с собой кучу плакатов"
                ),
                ContentItem(
                    order = 12,
                    type = Step.type,
                    value = "Пройтись по центральным улицам"
                ),
                ContentItem(
                    order = 13,
                    type = Step.type,
                    value = "Послать чертилу нахуй"
                ),
                ContentItem(
                    order = 14,
                    type = Step.type,
                    value = "Заставить власть прекратить войну"
                ),
                ContentItem(
                    order = 15,
                    type = Description.type,
                    value = "24 февраля в 17:30.\nЦентральные площади городов.\nДо встречи!!"
                ),
            )
        )
    }
}