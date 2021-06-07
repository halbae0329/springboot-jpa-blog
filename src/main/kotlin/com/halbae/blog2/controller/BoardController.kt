package com.halbae.blog2.controller

import com.halbae.blog2.service.BoardService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class BoardController(private val boardService: BoardService) {

    @GetMapping("", "/")
    fun index(
        model: Model,
        @PageableDefault(size = 3, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): String { //컨트롤러에서 세션을 어떻게 찾는지?
        model.addAttribute("boards", boardService.getBoards(pageable))
        return "index" //viewResolver 작동!!
    }

    @GetMapping("/board/{id}")
    fun findById(@PathVariable id: Long, model: Model):String {
        model.addAttribute("board", boardService.getBoard(id))
        return "board/detail"
    }

    //USER 권한이 필요
    @GetMapping("/board/saveForm")
    fun saveForm(): String {
        return "/board/saveForm"
    }

    @GetMapping("/board/{id}/updateForm")
    fun updateForm(@PathVariable id: Long, model: Model): String {
        model.addAttribute("board", boardService.getBoard(id))
        return "/board/updateForm"
    }
}