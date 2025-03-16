package br.com.erudio.data.vo.v2

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.github.dozermapper.core.Mapping
import jakarta.persistence.*
import org.springframework.hateoas.RepresentationModel
import java.util.*

@JsonPropertyOrder("id", "author", "launch_date", "price", "title")
data class BooksVOV2 (
    @Mapping("id")
    @field: JsonProperty("id")
    var key: Long = 0,

    @field:JsonProperty("author")
    var author: String = "",

    @field:JsonProperty("launch_date")
    var launchDate: Date? = null,

    var price: Double = 0.0,

     var title: String = ""
): RepresentationModel<BooksVOV2>()