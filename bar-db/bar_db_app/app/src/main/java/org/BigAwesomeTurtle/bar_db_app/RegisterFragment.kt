package org.BigAwesomeTurtle.bar_db_app

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_register.*
import org.BigAwesomeTurtle.bar_db_app.model.ServerImpl
import org.BigAwesomeTurtle.bar_db_app.model.sha512_5
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val originalDrawable = register_edit_first_name.background
        btn_register_cancel.setOnClickListener {
            activity?.onBackPressed()
        }

        val server = ServerImpl(activity)

        btn_register_apply.setOnClickListener {
            var check = true
            server.db_get(
                mapOf(
                    Pair("table", "regular_customer"),
                    Pair("colons", listOf("id")),
                    Pair("where", listOf("login", "\'" + register_edit_login.text.toString() + "\'", "=")
                    )
                )
            ){

                val curr = JSONArray(it)
                if (curr.length() == 0) {
                    val fieldsList = listOf(
                        register_edit_last_name,
                        register_edit_first_name,
                        register_edit_middle_name,
                        register_edit_login,
                        register_edit_password,
                        register_edit_telephone
                    )
                    for (elem in fieldsList) {
                        elem.setHintTextColor(Color.parseColor("#80000000"))
                        elem.background = originalDrawable
                        if (elem.text.toString() == "") {
                            check = false
                            elem.background =
                                ContextCompat.getDrawable(context!!, R.drawable.et_cust)
                            elem.setHintTextColor(Color.parseColor("#80f9434f"))
                        }
                    }
                    if (check) {
                        val name = "%s %s %s".format(
                            register_edit_last_name.text,
                            register_edit_first_name.text,
                            register_edit_middle_name.text
                        )
                        server.db_insert(
                            mapOf(
                                Pair("table", "regular_customer"),
                                Pair("fields", listOf(
                                        "login",
                                        "password",
                                        "name",
                                        "telephone",
                                        "discount",
                                        "points"
                                    )
                                ),
                                Pair(
                                    "values", listOf(
                                        "\'" + register_edit_login.text.toString() + "\'",
                                        "\'" + register_edit_password.text.toString().sha512_5() + "\'",
                                        "\'" + name + "\'",
                                        "\'" + register_edit_telephone.text.toString() + "\'",
                                        0.05,
                                        100
                                    )
                                )
                            )
                        ){
                            val toast = Toast.makeText(
                                context,
                                "Регистрация прошла успешно",
                                Toast.LENGTH_SHORT
                            )
                            toast.setGravity(Gravity.BOTTOM, 0, 0)
                            toast.show()
                            fragmentManager!!.popBackStack()
                        }
                    }
                } else {
                    try {
                        JSONObject(curr[0].toString()).getString("id")
                        register_edit_login.background =
                            ContextCompat.getDrawable(context!!, R.drawable.et_cust)
                        register_edit_login.setHintTextColor(Color.parseColor("#80f9434f"))
                        register_edit_login.text.clear()
                        register_edit_login.hint = "Данный логин уже используется"
                    } catch (e: JSONException) {
                        val toast = Toast.makeText(
                            context,
                            "Что-то пошло не так, попробуйте ещё раз",
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.BOTTOM, 0, 0)
                        toast.show()
                    }
                }
            }

        }
    }
}
