package com.example.ntnews.model.entities

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ArticleDeserializer :JsonDeserializer<Article>{
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Article {
        val jsonObject = json.asJsonObject

        val headline = context.deserialize<Headline>(jsonObject["headline"], Headline::class.java) //Aynı şekilde "headline" alanı da özel bir sınıf olduğu için context.deserialize ile parse ediliyor.
        val byline = context.deserialize<Byline>(jsonObject["byline"], Byline::class.java)
        val pubDate= jsonObject["pub_date"]?.asString ?: ""
        val leadParagraph= jsonObject["lead_paragraph"]?.asString ?: ""
        val abstractText= jsonObject["abstract"]?.asString ?: ""
        val webUrl= jsonObject["web_url"]?.asString ?: ""
        val sectionName = jsonObject["section_name"]?.asString?: ""

        val multimediaElement = jsonObject["multimedia"]
         val multimedia :List<ArticleMultimedia> =when {
             multimediaElement==null || multimediaElement.isJsonNull -> emptyList()
              multimediaElement.isJsonArray -> {
                  multimediaElement.asJsonArray.map {
                      context.deserialize(it,ArticleMultimedia::class.java)
                  }
              }

             multimediaElement.isJsonObject -> {
                 listOf(context.deserialize(multimediaElement, ArticleMultimedia::class.java))
             }
             else -> emptyList()
         }

        return Article(
            headline = headline,
            byline = byline,
            pubDate = pubDate,
            multimedia = multimedia,
            leadParagraph = leadParagraph,
            abstract = abstractText,
            webUrl = webUrl,
            sectionName = sectionName
        )


    }
}