package com.halbae.blog2.test

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class TempControllerTest {

    //http://localhost:8000/blog/temp/home
    @GetMapping("/temp/home")
    fun tempHome(): String {
        System.out.println("tempHome()")
        //파일 리턴 기본 경로: src/main/resources/static
        //리턴명: /home.html
        //풀 경로: src/main/resources/static/home.html
        return "/home.html"
    }

    @GetMapping("/temp/img")
    fun tempImg(): String {
        System.out.println("tempImg()")
        //파일 리턴 기본 경로: src/main/resources/static
        //리턴명: /a.png
        //풀 경로: src/main/resources/static/a.png
        return "/a.png"
    }

    @GetMapping("/temp/jsp")
    fun tempJsp(): String {
        System.out.println("tempJsp()")
        //prefix: /WEB-INF/views/
        //suffix: .jsp
        //풀 경로: /WEB-INF/views/test.jsp
        return "test"
    }
}