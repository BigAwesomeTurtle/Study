package org.bigawesometurtle.blackjack.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.bigawesometurtle.blackjack.model.Card
import org.bigawesometurtle.blackjack.model.GameSession

class GameViewModel : ViewModel() {

    val playerCards = MutableLiveData<List<Card>>()
    val dealerCards = MutableLiveData<List<Card>>()
    val playerValue = MutableLiveData(0)
    val dealerValue = MutableLiveData(0)
    val gameRes = MutableLiveData<Int>()
    val haveAce = MutableLiveData<Boolean>()

    private lateinit var session: GameSession

    fun initGame() {
        session = GameSession()
        session.initializeGame()
        val values = session.dealInitialCards()
        playerValue.value = values.first
        dealerValue.value = values.second
        if (playerValue.value == 21) {
            val dealerVal = session.checkBlackJack()
            dealerValue.value = dealerVal
            if (dealerVal != 21) gameRes.value = 3
            else gameRes.value = 1
        } else haveAce.value = session.haveAce()
        playerCards.value = session.getPlayerCards()
        dealerCards.value = session.getDealerCards()
    }

    fun onHit() {
        playerValue.value = session.hit()
        playerCards.value = session.getPlayerCards()
        if (playerValue.value!! > 21) gameRes.value = 0
        else if (playerCards.value!!.size >= 5 || playerValue.value == 21) onStand()
        else haveAce.value = session.haveAce()
    }

    fun onStand() {
        haveAce.value = false
        dealerValue.value = session.stand()
        dealerCards.value = session.getDealerCards()
        val player = playerValue.value!!
        val dealer = dealerValue.value!!
        if (dealer > 21) gameRes.value = 2
        else {
            when {
                dealer > player -> gameRes.value = 0
                dealer == player -> gameRes.value = 1
                else -> gameRes.value = 2
            }

        }
    }


}