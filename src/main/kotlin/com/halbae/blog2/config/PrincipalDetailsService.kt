package com.halbae.blog2.config

import com.halbae.blog2.model.User
import com.halbae.blog2.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PrincipalDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    //스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
    //password 부분 처리는 알아서 함.
    //username 이 DB에 있는지만 확인해 주면 됨.
    override fun loadUserByUsername(username: String?): UserDetails {
        val principal: User = userRepository.findByUsername(username)
            .orElseThrow {
                UsernameNotFoundException("해당사용자를 찾을 수 없습니다. username:" + username)
            }
        return PrincipalDetails(principal) //시큐리티의 세션에 유저 정보가 저장됨
    }
}