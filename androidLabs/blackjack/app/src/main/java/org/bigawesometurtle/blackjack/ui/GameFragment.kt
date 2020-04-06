package org.bigawesometurtle.blackjack.ui

import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game_bottom1.*
import kotlinx.android.synthetic.main.fragment_game_bottom2.*
import kotlinx.android.synthetic.main.fragment_game_top.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.bigawesometurtle.blackjack.App

import org.bigawesometurtle.blackjack.model.Card
import org.bigawesometurtle.blackjack.R
import org.bigawesometurtle.blackjack.database.RoomEntry
import org.bigawesometurtle.blackjack.viewModel.GameViewModel
import java.io.File
import java.io.FileNotFoundException


class GameFragment : BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_game

    private var total = 2500
    private var bet = 0
    private lateinit var viewModel: GameViewModel
    private lateinit var imgViewsPlayer: List<ImageView>
    private lateinit var imgViewsDealer: List<ImageView>

    private val database by lazy {
        (activity?.application as App).database.scoreDao()
    }


    private lateinit var sharedPref: SharedPreferences

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        viewModel.playerCards.observe(viewLifecycleOwner, Observer {
            uploadCards(it, imgViewsPlayer)
        })
        viewModel.dealerCards.observe(viewLifecycleOwner, Observer {
            uploadCards(it, imgViewsDealer)
        })

        viewModel.playerValue.observe(viewLifecycleOwner, Observer {
            curr_player.text = it.toString()
        })
        viewModel.dealerValue.observe(viewLifecycleOwner, Observer {
            curr_dealer.text = it.toString()
        })
        viewModel.gameRes.observe(viewLifecycleOwner, Observer {
            endDialog(it)
            updateTotal(it)
            playAgain()
        })
        viewModel.haveAce.observe(viewLifecycleOwner, Observer {
            if (it) curr_player.text =
                    "%s/%s".format(viewModel.playerValue.value!! - 10, viewModel.playerValue.value)
            else curr_player.text = viewModel.playerValue.value!!.toString()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgViewsPlayer =
                listOf(player_card_1, player_card_2, player_card_3, player_card_4, player_card_5)
        imgViewsDealer =
                listOf(dealer_card_1, dealer_card_2, dealer_card_3, dealer_card_4, dealer_card_5)

        btn_start.setOnClickListener { startGame() }
        btn_bet_ten.setOnClickListener { onBet(10) }
        btn_bet_fifty.setOnClickListener { onBet(50) }
        btn_bet_hundred.setOnClickListener { onBet(100) }
        btn_hit.setOnClickListener { viewModel.onHit() }
        btn_stand.setOnClickListener { viewModel.onStand() }
        btn_remove.setOnClickListener { onRemoveBet() }
        setCardsBack()
    }

    private fun startGame() {
        if (bet > 0) {
            include_fragment_bottom1.visibility = View.GONE
            include_fragment_bottom2.visibility = View.VISIBLE
            setCardsBack()
            viewModel.initGame()
        } else {
            val toast = Toast.makeText(context, "You need to make a bet", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            toast.show()
        }

    }

    private fun onBet(amount: Int) {
        if (total - amount >= 0) {
            bet += amount
            total -= amount
        } else {
            bet += total
            total = 0
        }
        bet_amount.text = bet.toString()
        money_amount.text = total.toString()
        if (btn_remove.visibility != View.VISIBLE) btn_remove.visibility = View.VISIBLE
    }

    private fun onRemoveBet() {
        total += bet
        bet = 0
        bet_amount.text = bet.toString()
        money_amount.text = total.toString()
        btn_remove.visibility = View.INVISIBLE
    }

    private fun uploadCards(cards: List<Card>, imgViews: List<ImageView>) {
        for (i in cards.indices) {
            val context = imgViews[i].context
            val card = cards[i]
            val id = context.resources.getIdentifier("ic$card", "drawable", context.packageName)
            imgViews[i].setImageResource(id)
        }
    }

    private fun endDialog(res: Int) {
        when (res) {
            0 -> {
                val toast = Toast.makeText(context, "You lose", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
                updateScore(0)
            }
            1 -> {
                val toast = Toast.makeText(context, "Draw", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
            }
            2 -> {
                val toast =
                        Toast.makeText(context, "You won\n+%s".format(bet * res), Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
                updateScore(1)

            }
            3 -> {
                val toast = Toast.makeText(
                        context,
                        "BlackJack!!!\n+%s".format(bet * res),
                        Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.BOTTOM, 0, 0)
                toast.show()
                updateScore(1)
            }
        }

    }

    private fun updateScore(coef: Int) {
        GlobalScope.launch {
            val player = arguments?.getString("player_name") ?: ""
            val oldResult = database.getByName(player)
            val lose = if (coef == 0) 1 else 0
            if (oldResult == null) {
                database.insert(RoomEntry(playerName = player, win = coef, lose = lose))
            } else {
                database.updateScore(player, oldResult.win + coef, oldResult.lose + lose)
            }
        }
    }

    private fun updateTotal(coef: Int) {
        total += coef * bet
        bet = 0
        bet_amount.text = bet.toString()
        money_amount.text = total.toString()
    }

    private fun playAgain() {
        include_fragment_bottom1.visibility = View.VISIBLE
        include_fragment_bottom2.visibility = View.GONE
        btn_remove.visibility = View.INVISIBLE
    }

    private fun setCardsBack() {
        sharedPref = context!!.getSharedPreferences("myPrefs", AppCompatActivity.MODE_PRIVATE)
        val backs = listOf(R.drawable.card_back_1, R.drawable.card_back_2, R.drawable.card_back_3)
        val back = sharedPref.getInt("chosenBack", 1)
        if (back == 4) {
            try {
                for (i in 0 until 5) {
                    imgViewsPlayer[i].setImageURI(Uri.fromFile(File(context!!.filesDir.absolutePath + "/user_back")))
                    imgViewsDealer[i].setImageURI(Uri.fromFile(File(context!!.filesDir.absolutePath + "/user_back")))
                }
            } catch (e: FileNotFoundException) {
                for (i in 0 until 5) {
                    imgViewsPlayer[i].setImageResource(backs[0])
                    imgViewsDealer[i].setImageResource(backs[0])
                }
                sharedPref.edit().putInt("chosenBack", 1).apply()
            }

        } else {
            for (i in 0 until 5) {
                imgViewsPlayer[i].setImageResource(backs[back - 1])
                imgViewsDealer[i].setImageResource(backs[back - 1])
            }
        }

    }

}
