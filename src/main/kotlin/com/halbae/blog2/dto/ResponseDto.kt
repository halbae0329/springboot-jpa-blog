package com.halbae.blog2.dto

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@NoArgsConstructor
@AllArgsConstructor
data class ResponseDto<T>(
    val status: Int,
    val data: T?
)