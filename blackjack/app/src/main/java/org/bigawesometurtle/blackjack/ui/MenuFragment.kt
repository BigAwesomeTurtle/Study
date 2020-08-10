package org.bigawesometurtle.blackjack.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.dialog_player_name.view.*
import kotlinx.android.synthetic.main.fragment_menu.*
import org.bigawesometurtle.blackjack.R


class MenuFragment : BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_menu
    private val bundle = Bundle()
    private lateinit var dialogView: View

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_player_name, null)

        score_button.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_scoreFragment)
        }

        start_game_button.setOnClickListener {
            showDialog()
        }
        choose_card_cover_button.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_chooseCoverActivity)
        }

    }

    private fun showDialog() {
        val namesDialog = AlertDialog.Builder(requireContext())
        if (dialogView.parent != null)
            (dialogView.parent as ViewGroup).removeView(dialogView)
        namesDialog.apply {
            setView(dialogView)
            setTitle(R.string.player_name_title)
            setPositiveButton(R.string.start) { _, _ ->
                val firstPlayerName = dialogView.first_player_text.text.toString()
                bundle.putString(
                        "player_name",
                        if (firstPlayerName == "") resources.getString(R.string.first_player) else firstPlayerName
                )
                findNavController().navigate(R.id.action_menuFragment_to_gameFragment, bundle)
            }
            setNegativeButton(R.string.cancel) { _, _ -> }
            show()
        }
    }


}
