package com.halbae.blog2.test

import com.halbae.blog2.model.RolyType
import com.halbae.blog2.model.User
import com.halbae.blog2.repository.UserRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException
import java.util.*
import java.util.function.Supplier
import javax.transaction.Transactional

@RestController
class DummyControllerTest(private val userRepository: UserRepository) {

    //http://localhost:8000/blog/dummy/join
    //http의 body에 username, password, email 데이터를 가지고
    @PostMapping("/dummy/join")
    //fun join(username: String, password: String, email:String): String {
    fun join(user: User): String {
        println("username:" + user.username)
        println("password:" + user.password)
        println("email:" + user.email)

        user.role = RolyType.USER
        userRepository.save(user)

        return "회원가입이 완료 되었습니다."
    }

    //{id} 주소로 파라미터를 전달 받을 수 있음
    //http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    fun detail(@PathVariable id: Long): User {
        val user = userRepository.findById(id).orElseThrow(Supplier {
            IllegalArgumentException("해당 유저는 없습니다. id:" + id)
        })
        //요청 : 웹브라우저
        //user 객체 = 자바 오브젝트
        //스프링부트 = MessageConverter 라는 애가 응답시에 자동으로 작동
        //만약에 자바 오프젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
        //user 오브젝트를 json으로 변환해서 브라우저에게 줌
        return user
    }

    @GetMapping("/dummy/users")
    fun list(): List<User> {
        return userRepository.findAll()
    }

    @GetMapping("/dummy/user")
    fun pageList(@PageableDefault(size = 2, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable)
            : List<User> {
        val pages = userRepository.findAll(pageable)
        val users = pages.content
        return users
    }

    //save 함수는 id를 전달하지 않으면 insert를 해주고
    //save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    //save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert 해 줌
    @Transactional //함수 종료시에 자동 commit 됨
    @PutMapping("/dummy/user/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody requestUser: User): User? {
        //json 데이터를 요청 => MessageConverter의 Jackson 라이브러리가 자바 오프젝트로 변환해서 받아줌
        println("id:" + id)
        println("requestUser:" + requestUser)

        var user = userRepository.findById(id).orElseThrow {
            IllegalArgumentException("수정에 실패 하였습니다.")
        }

        user.password = requestUser.password
        user.email = requestUser.email

        //userRepository.save(requestUser)

        //더티 체킹
        return user
    }

    @DeleteMapping("/dummy/user/{id}")
    fun delete(@PathVariable id: Long): String {
        try {
            userRepository.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            return "삭제에 실패 하였습니다. 해당 id는 DB에 없습니다."
        }

        return "삭제 되었습니다. id:" + id
    }
}