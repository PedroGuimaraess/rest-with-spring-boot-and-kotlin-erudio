package br.com.erudio.config//package br.com.erudio.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

//@Configuration
class WebServerConfiguration {

    fun addCorsConfig(): WebMvcConfigurer {
        return object : WebMvcConfigurer{
            override fun addCorsMappings(registry: CorsRegistry) {
                super.addCorsMappings(registry)
            }
        }
    }

}