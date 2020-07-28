package org.BigAwesomeTurtle.bar_db_app


import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_account.*
import org.BigAwesomeTurtle.bar_db_app.model.ServerImpl
import org.BigAwesomeTurtle.bar_db_app.model.sha512_5
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val login = arguments?.getString("login")
        lateinit var change_name: String
        lateinit var change_teleph: String
        lateinit var curr_pass: String

        if (login == null) {
            somethingWentWrong()
        } else {

            val server = ServerImpl(activity)

            server.db_get(
                mapOf(
                    Pair("table", "regular_customer"),
                    Pair(
                        "colons",
                        listOf(
                            "name",
                            "password",
                            "telephone",
                            "discount",
                            "points",
                            "favourite_bar_id"
                        )
                    ),
                    Pair("where", listOf("login", "\'" + login + "\'", "="))
                )
            ){
                val resp=JSONArray(it)
                val objResp = JSONObject(resp[0].toString())
                curr_pass = objResp.getString("password")
                textView_login_name.text = login
                textView_name.text = objResp.getString("name")
                textView_discount.text = "%s%%".format(
                    (objResp.getString("discount").toDouble() * 100).toInt().toString()
                )
                textView_bonus.text = objResp.getString("points")
                textView_teleph.text = objResp.getString("telephone")
                val favourite_bar_id = objResp.getString("favourite_bar_id")
                server.db_get(
                    mapOf(
                        Pair("table", "bar"),
                        Pair("colons", listOf("address")),
                        Pair("where", listOf("id", favourite_bar_id, "="))
                    )
                ){
                    try{
                        val resp1=JSONArray(it)
                        val objResp1 = JSONObject(resp1[0].toString())
                        textView_favor_bar.text = objResp1.getString("address")
                    }
                    catch (e: JSONException){
                        textView_favor_bar.text=""
                    }
                }
            }
            
            btn_change_password.setOnClickListener {

                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                val inflater = activity!!.layoutInflater
                val dialog_view = inflater.inflate(R.layout.password_change_dialog, null)
                val dialog = builder.setView(dialog_view)
                    .setPositiveButton("Confirm", null)
                    .setNegativeButton("Cancel", null).create()
                dialog.setOnShowListener {
                    val button_pos: Button =
                        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
                    val button_neg: Button =
                        (dialog).getButton(AlertDialog.BUTTON_NEGATIVE)

                    button_pos.setOnClickListener {
                        val old_pass = dialog_view.findViewById<EditText>(R.id.old_pass_edit)
                        val new_pass = dialog_view.findViewById<EditText>(R.id.new_pass_edit)
                        val conf_pass = dialog_view.findViewById<EditText>(R.id.confirm_pass_edit)
                        if (old_pass.text.toString().sha512_5() != curr_pass) {
                            old_pass.text.clear()
                            old_pass.background =
                                ContextCompat.getDrawable(context!!, R.drawable.et_cust)
                            old_pass.hint = "Неверный пароль"
                            old_pass.setHintTextColor(Color.parseColor("#80f9434f"))
                        } else if (new_pass.text.toString() != conf_pass.text.toString()) {
                            new_pass.text.clear()
                            conf_pass.text.clear()
                            new_pass.background =
                                ContextCompat.getDrawable(context!!, R.drawable.et_cust)
                            conf_pass.background =
                                ContextCompat.getDrawable(context!!, R.drawable.et_cust)
                            new_pass.hint = "Пароли не совпадают"
                            conf_pass.hint = "Пароли не совпадают"
                            new_pass.setHintTextColor(Color.parseColor("#80f9434f"))
                            conf_pass.setHintTextColor(Color.parseColor("#80f9434f"))
                        } else {
                            server.db_update(
                                mapOf(
                                    Pair("table", "regular_customer"),
                                    Pair("field", "password"),
                                    Pair(
                                        "value",
                                        "\'" + new_pass.text.toString().sha512_5() + "\'"
                                    ),
                                    Pair("where", listOf("login", "\'" + login + "\'", "="))
                                )
                            ) {
                                val toast = Toast.makeText(
                                    context,
                                    "Пароль успешно изменён",
                                    Toast.LENGTH_SHORT
                                )
                                toast.setGravity(Gravity.BOTTOM, 0, 0)
                                toast.show()
                                dialog.dismiss()
                            }
                        }
                    }
                    button_neg.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
            }

            btn_delete_account.setOnClickListener {

                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                val inflater = activity!!.layoutInflater
                val dialog_view = inflater.inflate(R.layout.remove_acc_dialog, null)

                val dialog = builder.setView(dialog_view)
                    .setPositiveButton("Удалить", null)
                    .setNegativeButton("Отмена", null).create()
                dialog.setOnShowListener {
                    val button_pos: Button =
                        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
                    val button_neg: Button =
                        (dialog).getButton(AlertDialog.BUTTON_NEGATIVE)

                    button_pos.setOnClickListener {
                        val pass =
                            dialog_view.findViewById<EditText>(R.id.remove_acc_pass_check_edit)

                        if (pass.text.toString().sha512_5() != curr_pass) {
                            pass.text.clear()
                            pass.background =
                                ContextCompat.getDrawable(context!!, R.drawable.et_cust)
                            pass.hint = "Неверный пароль"
                            pass.setHintTextColor(Color.parseColor("#80f9434f"))
                        } else {
                            server.db_remove(
                                mapOf(
                                    Pair("table", "regular_customer"),
                                    Pair("where", listOf("login", "\'" + login + "\'", "="))
                                )
                            ) {
                                (activity as MainActivity).authToken=null
                                (activity as MainActivity).authLogin=null
                                val toast = Toast.makeText(
                                    context,
                                    "Аккаунт успешно удалён",
                                    Toast.LENGTH_SHORT
                                )
                                toast.setGravity(Gravity.BOTTOM, 0, 0)
                                toast.show()
                                dialog.dismiss()
                                activity?.onBackPressed()
                            }
                            
                        }
                    }
                    button_neg.setOnClickListener {
                        dialog.dismiss()
                    }
                }
                dialog.show()
            }

            btn_exit_account.setOnClickListener {
                (activity as MainActivity).authLogin = null
                (activity as MainActivity).authToken = null
                activity?.onBackPressed()
            }

            textView_name_change.setOnClickListener {
                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                val inflater = activity!!.layoutInflater
                val dialog_view = inflater.inflate(R.layout.name_change_dialog, null)
                builder.setView(dialog_view)
                    .setPositiveButton("Confirm") { _, _ ->
                        change_name =
                            dialog_view.findViewById<EditText>(R.id.name_change_edit).text.toString()
                        server.db_update(
                            mapOf(
                                Pair("table", "regular_customer"),
                                Pair("field", "name"),
                                Pair("value", "\'" + change_name + "\'"),
                                Pair("where", listOf("login", "\'" + login + "\'", "="))
                            )
                        ){
                            textView_name.text = change_name
                        }
                        
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }.create().show()

            }

            textView_teleph_change.setOnClickListener {
                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                val inflater = activity!!.layoutInflater
                val dialog_view = inflater.inflate(R.layout.teleph_change_dialog, null)
                builder.setView(dialog_view)
                    .setPositiveButton("Confirm") { _, _ ->
                        change_teleph =
                            dialog_view.findViewById<EditText>(R.id.teleph_change_edit).text.toString()
                        server.db_update(
                            mapOf(
                                Pair("table", "regular_customer"),
                                Pair("field", "telephone"),
                                Pair("value", "\'" + change_teleph + "\'"),
                                Pair("where", listOf("login", "\'" + login + "\'", "="))
                            )
                        ){
                            textView_teleph.text = change_teleph
                        }
                        
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }.create().show()

            }

            textView_favor_bar_change.setOnClickListener {

                server.db_get(
                    mapOf(
                        Pair("table", "bar"),
                        Pair("colons", listOf("id", "address"))
                    )
                ){
                    val resp = JSONArray(it)
                    val address_list = mutableListOf<String>()
                    val id_list = mutableListOf<Int>()
                    for (i in 0 until resp.length()) {
                        address_list.add(JSONObject(resp[i].toString()).getString("address"))
                        id_list.add(JSONObject(resp[i].toString()).getString("id").toInt())
                    }

                    val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                    val inflater = activity!!.layoutInflater
                    val dialog_view = inflater.inflate(R.layout.teleph_change_dialog, null)
                    builder.setView(dialog_view)
                        .setTitle("Выберите бар")
                        .setItems(
                            address_list.toTypedArray(),
                            DialogInterface.OnClickListener { _, which ->
                                textView_favor_bar.text = address_list[which]
                                server.db_update(
                                    mapOf(
                                        Pair("table", "regular_customer"),
                                        Pair("field", "favourite_bar_id"),
                                        Pair("value", id_list[which]),
                                        Pair("where", listOf("login", "\'" + login + "\'", "="))
                                    )
                                ){}
                            }).create().show()
                }
            }

        }
    }

    private fun somethingWentWrong() {
        val toast = Toast.makeText(
            context,
            "Что-то пошло не так. Невозможно перейти в личный кабинет",
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
        fragmentManager!!.popBackStack()
    }

}
