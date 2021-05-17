package com.halbae.blog2.test

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BlogControllerTest {

    @GetMapping("/test/hello")
    fun hello(): String {
        return "<h1>hello spring boot</h1>"
    }
}