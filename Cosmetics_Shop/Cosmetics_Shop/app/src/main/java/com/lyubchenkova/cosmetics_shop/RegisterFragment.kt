package com.lyubchenkova.cosmetics_shop

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.lyubchenkova.cosmetics_shop.Utils.CosmShopHash
import kotlinx.android.synthetic.main.fragment_register.*
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

        val queue = Volley.newRequestQueue(context)

        signupBackBtn.setOnClickListener {
            activity?.onBackPressed()
        }

        signupRegisterBtn.setOnClickListener {

            var check = true
            val fieldsList = listOf(
                signupNameEditText,
                signupSurnameEditText,
                signupPhoneEditText,
                signupEmailEditText,
                signupLoginEditText,
                signupPasswordEditText
            )
            for (elem in fieldsList) {
                if (elem.text.toString() == "") {
                    check = false
                    val toast = Toast.makeText(
                        context,
                        "Please, fill empty fields",
                        Toast.LENGTH_SHORT
                    )
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
            }
            if (check) {
                val request = StringRequest(
                    Request.Method.GET,
                    "HTTP://192.168.0.101:8080/isClientExists/" + signupLoginEditText.text.toString(),
                    Response.Listener { response ->
                        val curr = JSONArray(response)

                        if (JSONObject(curr[0].toString()).getString("result") == "False") {
                            val hashed_pass = signupPasswordEditText.text.toString()
                                .CosmShopHash(signupLoginEditText.text.toString())
                            val insert_request = StringRequest(
                                Request.Method.PUT,
                                "HTTP://192.168.0.101:8080/addClient/%s/%s/%s/%s/%s/%s".format(
                                    signupNameEditText.text.toString(),
                                    signupSurnameEditText.text.toString(),
                                    signupPhoneEditText.text.toString(),
                                    signupEmailEditText.text.toString(),
                                    signupLoginEditText.text.toString(),
                                    hashed_pass
                                ),
                                Response.Listener { _ ->
                                    val toast = Toast.makeText(
                                        context,
                                        "Регистрация прошла успешно",
                                        Toast.LENGTH_SHORT
                                    )
                                    toast.setGravity(Gravity.BOTTOM, 0, 0)
                                    toast.show()
                                    activity?.onBackPressed()
                                },
                                Response.ErrorListener {
                                    val toast = Toast.makeText(
                                        context,
                                        "Something went wrong while accessing the server",
                                        Toast.LENGTH_SHORT
                                    )
                                    toast.setGravity(Gravity.CENTER, 0, 0)
                                    toast.show()
                                })
                            queue.add(insert_request)
                        } else {
                            try {
                                if (JSONObject(curr[0].toString()).getString("result") == "True") {
                                    signupLoginEditText.hint = "Login is already taken"
                                    val toast = Toast.makeText(
                                        context,
                                        "Login is already taken:(",
                                        Toast.LENGTH_SHORT
                                    )
                                    toast.setGravity(Gravity.BOTTOM, 0, 0)
                                    toast.show()
                                }

                            } catch (e: JSONException) {
                                val toast = Toast.makeText(
                                    context,
                                    "Something is wrong, try again",
                                    Toast.LENGTH_SHORT
                                )
                                toast.setGravity(Gravity.BOTTOM, 0, 0)
                                toast.show()
                            }
                        }
                    }
                    ,
                    Response.ErrorListener {
                        val toast = Toast.makeText(
                            context,
                            "Something went wrong while accessing the server",
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    })
                queue.add(request)
            }
        }
    }
}
