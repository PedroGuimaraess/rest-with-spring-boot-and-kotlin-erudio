package br.com.erudio.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.Date

@Entity
@Table(name = "books")
data class Books (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "author", nullable = false, length = 100)
    var author: String = "",

    @Column(name = "launch_date", nullable = false, length = 100)
    var launch_date: Date? = null,

    @Column(name = "price", nullable = false)
    var price: Double = 0.0,

    @Column(name = "title", nullable = false, length = 100)
    var title: String = ""
)