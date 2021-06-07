package com.halbae.blog2.controller

import com.halbae.blog2.model.User
import com.halbae.blog2.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.util.*

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
//그냥 주소가 / 이면 index.jsp 허용
//static 이하에 있는 /js/**, /css/**, /image/** 허용
@Controller
class UserController(
    private val userService: UserService,
    private val authenticationManagerBean: AuthenticationManager,
    @Value("\${blog.key}") private val blogKey: String
) {

    @GetMapping("/auth/joinForm")
    fun joinForm(): String {
        return "user/joinForm"
    }

    @GetMapping("/auth/loginForm")
    fun loginForm(): String {
        return "user/loginForm"
    }

    @GetMapping("/user/updateForm")
    fun updateForm(): String {
        return "user/updateForm"
    }

    @GetMapping("/auth/kakao/callback")
    fun kakaoCallback(code: String): String {
        val oAuthToken = userService.getKakaoOAuthToken(code)
        val userProfile = userService.getKakaoUserProfile(oAuthToken.accessToken)

        //User 오브젝트 : username, password, email
        println("카카오 아이디:" + userProfile.id)
        println("카카오 이메일:" + userProfile.kakaoAccount.email)

        var kakaoUser = User()
        kakaoUser.username = userProfile.id.toString() + "_" + userProfile.kakaoAccount.email
        kakaoUser.password = blogKey
        kakaoUser.email = userProfile.kakaoAccount.email
        kakaoUser.oauth = "kakao"
        println("블로그 유저네임:" + kakaoUser.username)
        println("블로그 이메일:" + kakaoUser.email)
        println("블로그 패스워드:" + kakaoUser.password)

        //가입자 혹은 비가입자 체크 해서 처리
        val user = userService.findUserByUsername(kakaoUser.username!!)
        println("--------------:" + user)
        if (user.username == null) {
            println("기존 회원이 아닙니다.")
            userService.join(kakaoUser)
        }
        println("자동 로그인을 진행합니다.")

        val authentication: Authentication =
            authenticationManagerBean.authenticate(UsernamePasswordAuthenticationToken(kakaoUser.username, blogKey))
        SecurityContextHolder.getContext().authentication = authentication

        return "redirect:/"
    }
}