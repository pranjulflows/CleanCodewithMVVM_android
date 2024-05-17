package com.example.baseproject.data.error

class EmptyResponseBodyException : Exception(null, null)
class UnauthorizedException(message: String?) : Exception(message)
class ValidationException(message: String?) : Exception(message)