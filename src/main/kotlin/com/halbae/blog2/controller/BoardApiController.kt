package com.halbae.blog2.controller

import com.halbae.blog2.config.PrincipalDetails
import com.halbae.blog2.dto.ReplySaveRequestDto
import com.halbae.blog2.dto.ResponseDto
import com.halbae.blog2.model.Board
import com.halbae.blog2.model.Reply
import com.halbae.blog2.service.BoardService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class BoardApiController(private val boardService: BoardService) {

    @PostMapping("/api/board")
    fun save(@RequestBody board: Board, @AuthenticationPrincipal principal: PrincipalDetails): ResponseDto<Int> {
        boardService.save(board, principal.user)
        return ResponseDto(HttpStatus.OK.value(), 1)
    }

    @DeleteMapping("/api/board/{id}")
    fun deleteById(@PathVariable id: Long): ResponseDto<Int> {
        boardService.deleteById(id)
        return ResponseDto(HttpStatus.OK.value(), 1)
    }

    @PutMapping("/api/board/{id}")
    fun update(@PathVariable id: Long, @RequestBody board: Board): ResponseDto<Int> {
        boardService.updateBoard(id, board)
        return ResponseDto(HttpStatus.OK.value(), 1)
    }

    //데이터 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
    // dto 사용하지 않은 이유는!!
    @PostMapping("/api/board/{boradId}/reply")
    fun replySave(
        //@PathVariable boradId: Long, @RequestBody reply: Reply, @AuthenticationPrincipal principal: PrincipalDetails,
        @RequestBody replySaveRequestDto: ReplySaveRequestDto
    ): ResponseDto<Int> {
        //boardService.replySave(principal.user, boradId, reply)
        //boardService.replySave2(replySaveRequestDto)
        boardService.replySave3(replySaveRequestDto)
        return ResponseDto(HttpStatus.OK.value(), 1)
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    fun replyDelete(@PathVariable replyId: Long): ResponseDto<Int> {
        boardService.replyDelete(replyId)
        return ResponseDto(HttpStatus.OK.value(), 1)
    }
}