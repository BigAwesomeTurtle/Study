package com.lyubchenkova.cosmetics_shop

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.lyubchenkova.cosmetics_shop.Utils.CosmShopHash
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONArray
import org.json.JSONObject

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val queue = Volley.newRequestQueue(context)

        loginSignInBtn.setOnClickListener {

            val request = StringRequest(Request.Method.GET,
                "HTTP://192.168.0.101:8080/getClientPassword/" + loginLoginEditText.text.toString(),
                Response.Listener { response ->

                    if (JSONArray(response).length() != 1) {

                        loginLoginEditText.text.clear()
                        loginPasswordEditText.text.clear()

                        val toast = Toast.makeText(
                            context,
                            "Wrong login or password",
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    } else {

                        val serverPassHashed =
                            JSONObject(JSONArray(response)[0].toString()).getString("password")

                        if (serverPassHashed == loginPasswordEditText.text.toString()
                                .CosmShopHash(loginLoginEditText.text.toString())
                        ) {
                            (activity as LoginActivity).token = serverPassHashed
                            (activity as LoginActivity).login = loginLoginEditText.text.toString()

                            val bundle = bundleOf(
                                "login" to (activity as LoginActivity).login,
                                "token" to (activity as LoginActivity).token
                            )
                            bundle.putSerializable("HashMap", (activity as LoginActivity).cart)
                            findNavController().navigate(
                                R.id.action_loginFragment_to_mainActivity,
                                bundle
                            )
                            (activity as LoginActivity).finish()

                        } else {
                            loginLoginEditText.text.clear()
                            loginPasswordEditText.text.clear()
                            val toast = Toast.makeText(
                                context,
                                "Wrong login or password",
                                Toast.LENGTH_SHORT
                            )
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    }
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

            queue.add(request)
        }

        loginWithoutBtn.setOnClickListener {
            val bundle = bundleOf(
                "login" to null,
                "token" to null
            )
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity, bundle)
        }

        loginSignUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }
}


