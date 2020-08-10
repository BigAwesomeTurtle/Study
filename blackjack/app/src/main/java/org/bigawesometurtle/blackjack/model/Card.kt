package org.bigawesometurtle.blackjack.model

class Card(private val value: Int, private val figure: Char) {

    fun getCardScore(): Int {
        return if (value == 14) 11 else if (value > 10) 10 else value
    }

    override fun toString(): String {
        return "_" + value + "_" + figure
    }


}