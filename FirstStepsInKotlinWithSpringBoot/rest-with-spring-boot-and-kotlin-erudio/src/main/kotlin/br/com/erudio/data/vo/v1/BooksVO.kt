package br.com.erudio.data.vo.v1

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.github.dozermapper.core.Mapping
import jakarta.persistence.*
import org.springframework.hateoas.RepresentationModel
import java.util.*

@JsonPropertyOrder("id", "author", "price", "lastName", "title")
data class BooksVO (
    @Mapping("id")
    @field: JsonProperty("id")
    var key: Long = 0,

    @field:JsonProperty("author")
    var author: String = "",

    @field:JsonProperty("launch_date")
    var launch_date: Date? = null,

    var price: String = "",

     var title: String = ""
): RepresentationModel<BooksVO>()