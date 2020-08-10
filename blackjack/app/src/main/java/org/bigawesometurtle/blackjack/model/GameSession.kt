package org.bigawesometurtle.blackjack.model

class GameSession {

    private lateinit var deck: Deck
    private lateinit var players: Pair<Player, Dealer>

    fun getPlayerCards() = players.first.getCards()

    fun getDealerCards() = players.second.getCards()

    fun initializeGame() {
        deck = Deck()
        players = Pair(Player(), Dealer())
    }

    fun dealInitialCards(): Pair<Int, Int> {
        deck.dealCard(players.first)
        deck.dealCard(players.first)
        deck.dealCard(players.second)
        return Pair(players.first.total, players.second.total)
    }

    fun hit(): Int {
        deck.dealCard(players.first)
        return players.first.total
    }

    fun stand(): Int {
        while (players.second.wantToHit()) {
            deck.dealCard(players.second)
        }
        return players.second.total
    }

    fun checkBlackJack(): Int {
        deck.dealCard(players.second)
        return players.second.total
    }

    fun haveAce() = players.first.haveAce


}