package org.BigAwesomeTurtle.bar_db_app

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main_menu.*
import org.BigAwesomeTurtle.bar_db_app.model.ServerImpl
import org.BigAwesomeTurtle.bar_db_app.model.sha512_5
import org.json.JSONArray
import org.json.JSONObject


class MainMenuFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_menu.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_barMenuFragment)
        }
        btn_promo.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_promoFragment)
        }

        if ((activity as MainActivity).authToken != null) {
            val server = ServerImpl(activity)

            server.db_get(
                mapOf(
                    Pair("table", "regular_customer"),
                    Pair("colons", listOf("password")),
                    Pair(
                        "where",
                        listOf("login", "\'" + (activity as MainActivity).authLogin + "\'", "=")
                    )
                )
            ) {
                if (JSONObject(JSONArray(it)[0].toString()).getString("password") == (activity as MainActivity).authToken) {
                    btn_login.text = "  Личный кабинет  "
                    btn_login.setOnClickListener {
                        val bundle = bundleOf("login" to (activity as MainActivity).authLogin)
                        findNavController().navigate(
                            R.id.action_menuFragment_to_accountFragment,
                            bundle
                        )
                    }
                } else notAuthorized()
            }
        } else {
            notAuthorized()
        }
    }

    private fun notAuthorized() {
        btn_login.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            val inflater = activity!!.layoutInflater
            val dialog_view = inflater.inflate(R.layout.login_dialog, null)
            val dialog = builder.setView(dialog_view)
                .setPositiveButton("Log in", null)
                .setNegativeButton("Cancel", null)
                .setNeutralButton("Register", null).create()
            dialog.setOnShowListener {
                val button_pos: Button =
                    (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
                val button_neg: Button =
                    (dialog).getButton(AlertDialog.BUTTON_NEGATIVE)
                val button_neut: Button =
                    (dialog).getButton(AlertDialog.BUTTON_NEUTRAL)
                val login_test_edit = dialog_view.findViewById<EditText>(R.id.login_edit)
                val provided_pass_test_edit =
                    dialog_view.findViewById<EditText>(R.id.password_edit)
                val originalDrawable = provided_pass_test_edit.background

                button_pos.setOnClickListener {
                    val server_dial = ServerImpl(activity)

                    server_dial.db_get(
                        mapOf(
                            Pair("table", "regular_customer"),
                            Pair("colons", listOf("password")),
                            Pair("where", listOf("login", "\'" + login_test_edit.text + "\'", "="))
                        )
                    ) {
                        if (JSONArray(it).length() != 1) {

                            login_test_edit.text.clear()
                            provided_pass_test_edit.text.clear()
                            provided_pass_test_edit.hint = "Password"
                            provided_pass_test_edit.setHintTextColor(Color.parseColor("#80000000"))
                            login_test_edit.background =
                                ContextCompat.getDrawable(context!!, R.drawable.et_cust)
                            provided_pass_test_edit.background = originalDrawable
                            login_test_edit.hint = "Login does not exist"
                            login_test_edit.setHintTextColor(Color.parseColor("#80f9434f"))

                        } else {

                            login_test_edit.background = originalDrawable
                            login_test_edit.hint = "Login"
                            login_test_edit.setHintTextColor(Color.parseColor("#80000000"))

                            val server_hashed_pass =
                                JSONObject(JSONArray(it)[0].toString()).getString("password")

                            if (server_hashed_pass == provided_pass_test_edit.text.toString()
                                    .sha512_5()
                            ) {
                                dialog.dismiss()
                                (activity as MainActivity).authToken = server_hashed_pass
                                (activity as MainActivity).authLogin =
                                    login_test_edit.text.toString()
                                val bundle = bundleOf("login" to login_test_edit.text.toString())
                                findNavController().navigate(
                                    R.id.action_menuFragment_to_accountFragment,
                                    bundle
                                )

                            } else {
                                provided_pass_test_edit.text.clear()
                                provided_pass_test_edit.background =
                                    ContextCompat.getDrawable(context!!, R.drawable.et_cust)
                                provided_pass_test_edit.hint = "Wrong Password"
                                provided_pass_test_edit.setHintTextColor(Color.parseColor("#80f9434f"))
                            }
                        }

                    }


                }
                button_neg.setOnClickListener {
                    dialog.dismiss()
                }
                button_neut.setOnClickListener {
                    findNavController().navigate(R.id.action_menuFragment_to_registerFragment)
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
    }
}