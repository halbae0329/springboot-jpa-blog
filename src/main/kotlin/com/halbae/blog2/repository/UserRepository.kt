package com.halbae.blog2.repository

import com.halbae.blog2.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

//자동으로 bean 등록이 된다.
//@Repository 생략 가능
interface UserRepository : JpaRepository<User, Long> {
    //JPA Naming 전략
    //SELECT * FROM user WHERE username = ? AND password = ?;
   // fun findByUsernameAndPassword(username: String, password: String): User

//    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    fun login(username: String, password: String): User

    //SELECT * FROM user WHERE username = ?;
    fun findByUsername(username: String?): Optional<User>
}