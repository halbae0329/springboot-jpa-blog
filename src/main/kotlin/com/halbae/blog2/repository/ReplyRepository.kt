package com.halbae.blog2.repository

import com.halbae.blog2.model.Reply
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface ReplyRepository : JpaRepository<Reply, Long> {

    @Modifying
    @Query(value = "INSERT INTO reply(userId, boardId, content, createdAt) VALUES (?1, ?2, ?3, now())", nativeQuery = true)
    fun saveDto(userId: Long, boardId: Long, content: String): Int
}