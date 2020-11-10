package com.rollncode.test.core

abstract class UseCase<T> {
    abstract fun execute(): T
}