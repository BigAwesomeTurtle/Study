package org.bigawesometurtle.blackjack

import org.bigawesometurtle.blackjack.model.Card
import org.bigawesometurtle.blackjack.model.Player
import org.junit.Test

import org.junit.Assert.*

class UnitTest {

    @Test
    fun getCardScore() {
        assertEquals(5, Card(5, 'c').getCardScore())
        assertEquals(10, Card(10, 's').getCardScore())
        assertEquals(11, Card(14, 's').getCardScore())
        assertEquals(10, Card(13, 'h').getCardScore())
        assertEquals(10, Card(12, 'd').getCardScore())
        assertEquals(10, Card(11, 'd').getCardScore())
        assertEquals(2, Card(2, 'c').getCardScore())
    }

    @Test
    fun receiveCard() {
        val player = Player()
        player.receiveCard(Card(6, 'h'))
        assertEquals(6, player.total)
        assertFalse(player.haveAce)
        assertEquals(1, player.getCards().size)
        assertEquals(6, player.getCards()[0].getCardScore())
        player.receiveCard(Card(14, 's'))
        assertEquals(17, player.total)
        assertTrue(player.haveAce)
        assertEquals(2, player.getCards().size)
        assertEquals(6, player.getCards()[0].getCardScore())
        assertEquals(11, player.getCards()[1].getCardScore())
        player.receiveCard(Card(10, 'd'))
        assertEquals(17, player.total)
        assertFalse(player.haveAce)
        assertEquals(3, player.getCards().size)
        assertEquals(6, player.getCards()[0].getCardScore())
        assertEquals(11, player.getCards()[1].getCardScore())
        assertEquals(10, player.getCards()[2].getCardScore())
    }
}
