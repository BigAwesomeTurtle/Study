package org.bigawesometurtle.blackjack.model

import kotlin.random.Random

class Deck {
    private fun nextCard(): Card {
        val list = "cdhs".toCharArray()
        val value: Int = Random.nextInt(2, 15)
        val figure: Char = list.random()
        return Card(value, figure)
    }

    fun dealCard(player: Player) {
        player.receiveCard(nextCard())
    }

}