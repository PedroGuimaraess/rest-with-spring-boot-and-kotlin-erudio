package br.com.erudio.data.vo.v1

import java.util.*

data class TokenVO(
    var username: String? = null,
    val authenticated: Boolean? = null,
    val created: Date? = null,
    val expiration: Date? = null,
    var accessToken: String? = null,
    var refreshToken: String? = null
)
