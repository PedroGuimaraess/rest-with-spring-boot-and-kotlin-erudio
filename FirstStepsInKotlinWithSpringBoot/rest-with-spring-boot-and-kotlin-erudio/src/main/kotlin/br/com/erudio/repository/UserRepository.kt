package br.com.erudio.repository

import br.com.erudio.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository: JpaRepository<User?, Long> {

    @Query("SELECT u FROM User u WHERE u.userName = :userName ")
    fun findByUserName(@Param("userName") userName: String?): User?

}