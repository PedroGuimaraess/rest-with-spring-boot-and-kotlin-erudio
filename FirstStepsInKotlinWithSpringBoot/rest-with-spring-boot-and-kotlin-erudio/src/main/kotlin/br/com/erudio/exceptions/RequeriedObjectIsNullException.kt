package br.com.erudio.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception
import kotlin.RuntimeException

@ResponseStatus(HttpStatus.NOT_FOUND)
class RequeriedObjectIsNullException: RuntimeException {
    constructor(): super("It is not allowed to persist a null object")
    constructor(exception: String): super(exception)
}