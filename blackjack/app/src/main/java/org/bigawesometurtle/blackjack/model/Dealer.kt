package org.bigawesometurtle.blackjack.model

class Dealer : Player() {

    var dealerStop: Int = 17
    fun wantToHit(): Boolean {
        return total < dealerStop && this.getCards().size < 5
    }
}