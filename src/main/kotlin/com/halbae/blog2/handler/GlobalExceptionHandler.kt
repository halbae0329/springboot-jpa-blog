package com.halbae.blog2.handler

import com.halbae.blog2.dto.ResponseDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice
@RestController
class GlobalExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    fun handleArgumentException(e: Exception): ResponseDto<String> {
        return ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.message) //500
    }
}