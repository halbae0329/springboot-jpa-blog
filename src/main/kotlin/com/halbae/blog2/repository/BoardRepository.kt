package com.halbae.blog2.repository

import com.halbae.blog2.model.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {
}