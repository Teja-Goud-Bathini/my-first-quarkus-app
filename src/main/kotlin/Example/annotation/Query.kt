package com.example.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Query(
    val value: String,
    val nativeQuery: Boolean = true
)