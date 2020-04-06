package org.bigawesometurtle.blackjack.model

open class Player {
    private var cards = mutableListOf<Card>()
    var total: Int = 0
    var haveAce = false

    fun receiveCard(card: Card): Card {
        cards.add(card)
        val score = card.getCardScore()
        if (score == 11) haveAce = true
        if (total + score > 21 && haveAce) {
            total += score - 10
            haveAce = false
        } else total += score
        return card
    }

    fun getCards() = cards

}