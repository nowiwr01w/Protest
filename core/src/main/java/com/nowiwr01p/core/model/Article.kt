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
data class Article(
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
                    order = 3,
                    type = "title",
                    value = "Defiant Navalny has opposed Putin’s war in Ukraine from prison. His team fear for his safety"
                ),
                ContentItem(
                    order = 2,
                    type = "date",
                    value = "1673698311454"
                ),
                ContentItem(
                    order = 1,
                    type = "image",
                    value = "https://media.cnn.com/api/v1/images/stellar/prod/220322082848-05-alexei-navalny-guilty-032222.jpg"
                ),
                ContentItem(
                    order = 4,
                    type = "description",
                    value = "Surviving President Vladimir Putin’s poisoners was just a warm-up, not a warning, for Russian opposition politician Alexey Navalny. But his defiance, according to his political team, has put him in a race against time with the Russian autocrat.\n" +
                            "The question, according to Navalny’s chief investigator, Maria Pevchikh, is whether he can outlast Putin and his war in Ukraine – and on that the verdict is still out. “So far, touch wood, they haven’t gone ahead with trying to kill him again,” she told CNN.\n" +
                            "On January 17, 2021, undaunted and freshly recovered from an attempt on his life five months earlier – a near lethal dose of the deadly nerve agent Novichok delivered by Putin’s henchmen – Navalny boldly boarded a flight taking him right back into the Kremlin’s hands.\n" +
                            "By then, Navalny had become Putin’s nemesis. So strong is the Russian leader’s aversion to his challenger that even to this day he refuses to say his name.\n" +
                            "As Navalny stepped off the flight from Berlin onto the frigid tarmac at Moscow’s Sheremetyevo airport that snowy evening, he knew exactly what he was getting into. Just weeks before leaving Germany, he told CNN: “I understand that Putin hates me, I understand that people in the Kremlin are ready to kill.”\n" +
                            "Navalny’s path to understanding had come at a high cost. He knew in intimate and excruciating detail exactly how close he had come to death at the hands of Putin’s poisoners while on the political campaign trail in Siberia to support local candidates."
                ),
                ContentItem(
                    order = 7,
                    type = "description",
                    value = "When Putin was asked if he’d tried to have Navalny killed, he smirked, saying: “If there was such a desire, it would have been done.”\n" +
                            "Despite his denials, Putin’s desire was transparent: Navalny’s magnetism was positioning him as the Russian leader’s biggest political threat.\n" +
                            "Today he is the best-known anti-Putin politician in Russia and is putting his life on the line to break Putin’s stranglehold over Russians.\n" +
                            "Navalny’s team, who are in self-imposed exile for their safety, believe their boss is in a race for survival against Putin.\n" +
                            "Pevchikh, who heads Navalny’s investigative team and helped winkle out his would-be assassins, says the war in Ukraine – which Navalny has condemned from his prison cell behind bars – will bring Putin down. The question, she says, is whether Navalny can survive Putin. “It’s a bit of a race. You know, at this point, who lasts longer?”"
                ),
                ContentItem(
                    order = 5,
                    type = "image",
                    value = "https://media.cnn.com/api/v1/images/stellar/prod/221006132130-06-alexey-navalny-nobel.jpg"
                ),
                ContentItem(
                    order = 6,
                    type = "imageDescription",
                    value = "Alexey Navalny, his wife Yulia, opposition politician Lyubov Sobol and other demonstrators march in memory of murdered Kremlin critic Boris Nemtsov in downtown Moscow on February 29, 2020."
                ),
            )
        )
    }
}