package com.halbae.blog2.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.halbae.blog2.model.KakaoProfile
import com.halbae.blog2.model.OAuthToken
import com.halbae.blog2.model.RolyType
import com.halbae.blog2.model.User
import com.halbae.blog2.repository.UserRepository
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import java.lang.IllegalArgumentException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    //private val authenticationManagerBean: AuthenticationManager
) {

    @Transactional(readOnly = true)
    fun findUserByUsername(username: String): User {
        return userRepository.findByUsername(username).orElseGet {
            return@orElseGet User()
        }
    }

    @Transactional
    fun join(user: User) {
        val rawPassword = user.password
        val encPassword = passwordEncoder.encode(rawPassword)
        user.password = encPassword
        user.role = RolyType.USER
        userRepository.save(user)
    }

    @Transactional
    fun update(user: User) {
        //수정시에는 JPA 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화 된 User 오브젝트를 수정
        //select 를 해서 User 오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해
        //영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려 줌
        println("-----------:" + user)
        val persistence = userRepository.findById(user.id!!).orElseThrow {
            IllegalArgumentException("회원을 찾을 수 없습니다.")
        }

        //Validate 체크
        if (persistence.oauth == null || persistence.oauth == "") {
            val encPassword = passwordEncoder.encode(user.password)
            persistence.password = encPassword
            persistence.email = user.email
        }

        //회원 수정 함수 종료시 == 서비스 종료 == 트랜젝션이 종료 == commit 이 자동으로 됨
        //영속화 된 persistence 객체의 변화가 감지되면 더티체킹이 되어 update 문을 날려 줌

        //세션 등록 #2 => 동작 됨
//        val authentication: Authentication = authenticationManagerBean.authenticate(
//            UsernamePasswordAuthenticationToken(user.username, user.password)
//        )
//        SecurityContextHolder.getContext().authentication = authentication
    }

    //select 할 때 트랜젝션 시작, 서비스 종료시에 트랜젝션 종료 (정합성)
//    @Transactional(readOnly = true)
//    fun login(user: User): User {
//        return userRepository.findByUsernameAndPassword(user.username, user.password)
//    }

    fun getKakaoOAuthToken(code: String): OAuthToken {
        //post 방식으로 key=value 데이터를 요청(카카오쪽으로)
        //Retrofit2
        //OkHttp
        //RestTemplate

        val restTemplate: RestTemplate = RestTemplate()

        //HttpHeader 오브젝트 생성
        val httpHeaders: HttpHeaders = HttpHeaders()
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        //HttpBody 오브젝트 생성
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("grant_type", "authorization_code")
        params.add("client_id", "017676ab3a5765d55919325589160cf0")
        params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback")
        params.add("code", code)

        //HttpHeader 와 HttpBody 를 하나의 오브젝트에 담기
        val request: HttpEntity<MultiValueMap<String, String>> = HttpEntity(params, httpHeaders)

        //Http 요청하기 - Post 방식으로 - 그리고 response 변수의 응답 받음.
        val response: ResponseEntity<String> = restTemplate.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            request
        )

        //Gson, Json Simple, ObjectMapper
        val objectMapper: ObjectMapper = ObjectMapper()
        val oAuthToken = objectMapper.readValue(response.body, OAuthToken::class.java)
        println("oAuthToken:" + oAuthToken)

        return oAuthToken
    }

    fun getKakaoUserProfile(accessToken: String): KakaoProfile {
        val restTemplate: RestTemplate = RestTemplate()
        val httpHeaders: HttpHeaders = HttpHeaders()
        httpHeaders.add("Authorization", "Bearer " + accessToken)
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

        val request: HttpEntity<MultiValueMap<String, String>> = HttpEntity(httpHeaders)

        val response: ResponseEntity<String> = restTemplate.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            request
        )
        println("getKakaoUserProfile response:" + response)

        val objectMapper: ObjectMapper = ObjectMapper()
        val kakaoProfile = objectMapper.readValue(response.body, KakaoProfile::class.java)
        println("kakaoProfile:" + kakaoProfile)

        return kakaoProfile
    }
}