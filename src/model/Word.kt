package model

import entity.Type

data class Word(
    var word: String,
    var type: Type = Type.D,
    var r1: Int = 0,
    var r2: Int = 0,
    var r3: Int = 0,
    var op: String = "",
    var offset: Int = 0,
    val local: Int = 0,
    var jump: Int = 0,
    var temp: Int = 0,
)
