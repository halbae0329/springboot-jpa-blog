package com.halbae.blog2.service

import com.halbae.blog2.dto.ReplySaveRequestDto
import com.halbae.blog2.model.Board
import com.halbae.blog2.model.Reply
import com.halbae.blog2.model.User
import com.halbae.blog2.repository.BoardRepository
import com.halbae.blog2.repository.ReplyRepository
import com.halbae.blog2.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class BoardService(
    private val userRepository: UserRepository,
    private val boardRepository: BoardRepository,
    private val replyRepository: ReplyRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
) {

    @Transactional
    fun save(board: Board, user: User) {
        board.count = 0
        board.user = user
        boardRepository.save(board)
    }

    @Transactional(readOnly = true)
    fun getBoards(pageable: Pageable): Page<Board> {
        return boardRepository.findAll(pageable)
    }

    @Transactional(readOnly = true)
    fun getBoard(id: Long): Board {
        return boardRepository.findById(id).orElseThrow {
            IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.")
        }
    }

    @Transactional
    fun deleteById(id: Long) {
        boardRepository.deleteById(id)
    }

    @Transactional
    fun updateBoard(id: Long, requestBoard: Board) {
        val board = boardRepository.findById(id)
            .orElseThrow {
                IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.")
            } //영속화 완료
        board.title = requestBoard.title
        board.content = requestBoard.content
        //해당 함수 종료시 (Service가 종료될 때) 트랜젝션이 종료된다. 이때 더티채킹
        // - 자동 업데이트가 됨. db flush
    }

    @Transactional
    fun replySave(user: User, boardId: Long, requestReply: Reply) {
        val board = boardRepository.findById(boardId) .orElseThrow {
            IllegalArgumentException("댓글 쓰기 실패 : 아이디를 찾을 수 없습니다.")
        }
        requestReply.user = user
        requestReply.board = board

        replyRepository.save(requestReply)
    }

    @Transactional
    fun replySave2(replySaveRequestDto: ReplySaveRequestDto) {
        val user = userRepository.findById(replySaveRequestDto.userId) .orElseThrow {
            IllegalArgumentException("댓글 쓰기 실패 : 사용자 아이디를 찾을 수 없습니다.")
        }

        val board = boardRepository.findById(replySaveRequestDto.boardId) .orElseThrow {
            IllegalArgumentException("댓글 쓰기 실패 : 게시글 아이디를 찾을 수 없습니다.")
        }

        var reply: Reply = Reply()
        reply.user = user
        reply.board = board
        reply.content = replySaveRequestDto.content

        replyRepository.save(reply)
    }

    @Transactional
    fun replySave3(replySaveRequestDto: ReplySaveRequestDto) {
        replyRepository.saveDto(replySaveRequestDto.userId, replySaveRequestDto.boardId, replySaveRequestDto.content)
    }

    @Transactional
    fun replyDelete(replyId: Long) {
        replyRepository.deleteById(replyId)
    }
}