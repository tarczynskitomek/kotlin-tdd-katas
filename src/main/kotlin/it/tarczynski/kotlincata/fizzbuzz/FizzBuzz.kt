package it.tarczynski.kotlincata.fizzbuzz


fun isBuzz(num: Int): Boolean = num % 3 == 0

fun isFizz(num: Int): Boolean = num % 5 == 0

fun isFizzBuzz(num: Int): Boolean = isFizz(num) && isBuzz(num)

fun toFizzBuzzString(num: Int): String {
    if (isFizzBuzz(num)) return "FizzBuzz"
    if (isFizz(num)) return "Fizz"
    if (isBuzz(num)) return "Buzz"
    return num.toString()
}