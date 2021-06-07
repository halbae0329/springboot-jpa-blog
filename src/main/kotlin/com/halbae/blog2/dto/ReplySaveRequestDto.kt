package com.halbae.blog2.dto

data class ReplySaveRequestDto(
    var userId: Long,
    var boardId: Long,
    var content: String
)
