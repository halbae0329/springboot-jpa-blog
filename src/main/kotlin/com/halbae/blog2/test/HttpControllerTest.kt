package com.halbae.blog2.test

import org.springframework.web.bind.annotation.*

@RestController
class HttpControllerTest {

    //http://localhost:8080/http/get (select)
    @GetMapping("/http/get")
    fun getTest(id: Int, username: String): String {
        return "Get 요청 id=$id, username=$username"
    }

    @GetMapping("/http/get1")
    fun getTest1(member: Member): String {
        return "Get 요청 id=${member.id}, username=${member.username}, password=${member.password}, email=${member.email}"
    }

    //http://localhost:8080/http/post (insert)
    @PostMapping("/http/post")
    fun postTest(@RequestBody text: String): String {
        return "Post 요청 $text"
    }

    @PostMapping("/http/post1")
    fun postTest1(@RequestBody member: Member): String {
        return "Post 요청 id=${member.id}, username=${member.username}, password=${member.password}, email=${member.email}"
    }

    //http://localhost:8080/http/put (update)
    @PutMapping("/http/put")
    fun putTest(@RequestBody member: Member): String {
        return "Put 요청 id=${member.id}, username=${member.username}, password=${member.password}, email=${member.email}"
    }

    //http://localhost:8080/http/delete (delete)
    @DeleteMapping("/http/delete")
    fun deleteTest(): String {
        return "Delete 요청"
    }
}