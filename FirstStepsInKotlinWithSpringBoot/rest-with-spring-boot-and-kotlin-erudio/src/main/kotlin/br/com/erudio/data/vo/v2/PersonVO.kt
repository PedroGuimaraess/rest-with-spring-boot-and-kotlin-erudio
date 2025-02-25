package br.com.erudio.data.vo.v2

import com.github.dozermapper.core.Mapping
import jakarta.persistence.*
import java.util.Date


data class PersonVO (
    @Mapping("id")
    var key: Long = 0,

    var firstName: String = "",

    var lastName: String = "",

    var address: String = "",

    var gender: String = "",

    var birthDay: Date? = null
)