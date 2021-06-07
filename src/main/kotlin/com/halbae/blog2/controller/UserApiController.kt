package com.halbae.blog2.controller

import com.halbae.blog2.dto.ResponseDto
import com.halbae.blog2.model.User
import com.halbae.blog2.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserApiController(
    private val userService: UserService,
    private val authenticationManagerBean: AuthenticationManager,
) {

    @PostMapping("/auth/join")
    fun authJoin(@RequestBody user: User): ResponseDto<Int> {
        println("authJoin()")
        //자바 오브젝트를 json으로 변환해서 리턴(Jackson)
        userService.join(user)
        return ResponseDto(HttpStatus.OK.value(), 1)
    }

    @PutMapping("/user")
    fun update(
        @RequestBody user: User
        //,@AuthenticationPrincipal principal: PrincipalDetails
        //,session: HttpSession
    ): ResponseDto<Int> {
        userService.update(user)
        //여기서는 트랜젝션이 종료되기 때문에 DB에 값은 변경 완료
        //하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경

        //세센 등록 #1 ==> 안됨
//        val authentication = UsernamePasswordAuthenticationToken(principal, null, principal.authorities)
//        val securityContext: SecurityContext = SecurityContextHolder.getContext()
//        securityContext.authentication = authentication
//        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext)

        //세센 등록 #13 => 동작 됨
        val authentication: Authentication =
            authenticationManagerBean.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password))
        SecurityContextHolder.getContext().authentication = authentication

        return ResponseDto(HttpStatus.OK.value(), 1)
    }

//    @PostMapping("/api/user/login")
//    fun userLogin(@RequestBody user: User, session: HttpSession): ResponseDto<Int> {
//        println("userLogin()")
//        val principal: User = userService.login(user) //principal (접근주체)
//        if (principal != null) {
//            session.setAttribute("principal", principal)
//        }
//        return ResponseDto(HttpStatus.OK.value(), 1)
//    }
}