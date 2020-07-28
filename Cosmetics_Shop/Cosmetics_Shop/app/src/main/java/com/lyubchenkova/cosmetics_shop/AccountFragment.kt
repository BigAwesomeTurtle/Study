package com.lyubchenkova.cosmetics_shop

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.lyubchenkova.cosmetics_shop.Adapters.ClientAdapter
import kotlinx.android.synthetic.main.fragment_account.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login = (activity as MainActivity).login
        var curr_client_id: Int? = null
        val queue = Volley.newRequestQueue(context)
        val numInShop = mutableMapOf<Int, Int>()
        val costInShop = mutableMapOf<Int, Int>()
        var sum = 0
        val listOfPairs = mutableListOf<Pair<Int, Int>>()

        val clientInfoRequest = StringRequest(
            Request.Method.GET, "HTTP://192.168.0.101:8080/getClient/$login",
            Response.Listener { response ->

                val resp = JSONArray(response)
                val objResp = JSONObject(resp[0].toString())
                curr_client_id = objResp.getString("client_id").toInt()
                clientName.text = "Name: %s".format(objResp.getString("name"))
                clientSurname.text = "Surname: %s".format(objResp.getString("surname"))
                clientPhone.text = "Phone: %s".format(objResp.getString("phone"))
                clientEmail.text = "Email: %s".format(objResp.getString("email"))

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
        queue.add(clientInfoRequest)

        for (elem in (activity as MainActivity).cart) {
            listOfPairs.add(Pair(elem.key, elem.value))
        }

        for (elem in listOfPairs) {

            val cart_request = StringRequest(
                Request.Method.GET,
                "HTTP://192.168.0.101:8080/getItemByArticle/" + elem.first.toString(),
                Response.Listener { response ->
                    val curr = JSONObject(JSONArray(response)[0].toString())
                    sum += curr.getString("cost").toInt() * elem.second
                    client_price.text = sum.toString()
                    numInShop[elem.first] = curr.getString("num_in_shop").toInt()
                    costInShop[elem.first] = curr.getString("cost").toInt()
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
            queue.add(cart_request)

        }

        val recyclerLayoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            client_recycler_view.context,
            recyclerLayoutManager.orientation
        )
        client_recycler_view.apply {
            adapter = ClientAdapter(
                listOfPairs, (activity as MainActivity)
            )
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            addItemDecoration(dividerItemDecoration)

        }

        clientExitBtn.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java).apply {}
            startActivity(intent)
            activity?.finish()
        }

        clientPurchaseBtn.setOnClickListener {
            var check = 0
            val currentDate: String =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            for (elem in listOfPairs) {

                val numInShopUpdateRequest = StringRequest(
                    Request.Method.POST,
                    "HTTP://192.168.0.101:8080/updateNumInShop/%s/%s".format(
                        elem.first,
                        numInShop[elem.first]!! - elem.second
                    ),
                    Response.Listener { response ->

                        val curr = JSONArray(response)
                        if (JSONObject(curr[0].toString()).getString("result") == "True") {
                            check += 1
                        }
                        if (check == listOfPairs.size) {
                            val insertClientOrderRequest = StringRequest(
                                Request.Method.PUT,
                                "HTTP://192.168.0.101:8080/addClientOrders/%s/%s/%s/Collecting/%s".format(
                                    curr_client_id,
                                    currentDate,
                                    1,
                                    sum.toString()
                                ),
                                Response.Listener { response ->
                                    if (JSONObject(JSONArray(response)[0].toString()).getString("result") == "True") {
                                        val toast = Toast.makeText(
                                            context,
                                            "Successfully",
                                            Toast.LENGTH_SHORT
                                        )
                                        toast.setGravity(Gravity.BOTTOM, 0, 0)
                                        toast.show()

                                        val maxOrderIdRequest = StringRequest(
                                            Request.Method.GET,
                                            "HTTP://192.168.0.101:8080/getClientMaxOrderId/$curr_client_id",
                                            Response.Listener { response ->
                                                val curr_order_id =
                                                    JSONObject(JSONArray(response)[0].toString()).getString(
                                                        "max"
                                                    ).toInt()
                                                var itemToOrderCounter = 0
                                                for (i in 0 until listOfPairs.size) {
                                                    val elem_sum =
                                                        listOfPairs[i].second * costInShop[listOfPairs[i].first]!!

                                                    val insertItemListToOrderRequest =
                                                        StringRequest(
                                                            Request.Method.PUT,
                                                            "HTTP://192.168.0.101:8080/addItemListToOrder/%s/%s/%s/%s".format(
                                                                listOfPairs[i].first,
                                                                curr_order_id,
                                                                listOfPairs[i].second,
                                                                elem_sum
                                                            ),
                                                            Response.Listener { response ->
                                                                if (JSONObject(JSONArray(response)[0].toString()).getString(
                                                                        "result"
                                                                    ) == "True"
                                                                ) {
                                                                    itemToOrderCounter++
                                                                    if (itemToOrderCounter == listOfPairs.size) {
                                                                        (activity as MainActivity).cart =
                                                                            hashMapOf()
                                                                        val bundle =
                                                                            bundleOf("account_id" to curr_client_id.toString())
                                                                        findNavController().navigate(
                                                                            R.id.action_accountFragment_to_ordersFragment,
                                                                            bundle
                                                                        )
                                                                    }
                                                                }
                                                            },
                                                            Response.ErrorListener {
                                                                val toast2 = Toast.makeText(
                                                                    context,
                                                                    "Something went wrong while accessing the server",
                                                                    Toast.LENGTH_SHORT
                                                                )
                                                                toast2.setGravity(
                                                                    Gravity.CENTER,
                                                                    0,
                                                                    0
                                                                )
                                                                toast2.show()
                                                            })
                                                    queue.add(insertItemListToOrderRequest)

                                                }
                                            },
                                            Response.ErrorListener {
                                                val toast2 = Toast.makeText(
                                                    context,
                                                    "Something went wrong while accessing the server",
                                                    Toast.LENGTH_SHORT
                                                )
                                                toast2.setGravity(Gravity.CENTER, 0, 0)
                                                toast2.show()
                                            })
                                        queue.add(maxOrderIdRequest)

                                    } else {
                                        val toast = Toast.makeText(
                                            context,
                                            "Something went wrong while making your order",
                                            Toast.LENGTH_SHORT
                                        )
                                        toast.setGravity(Gravity.CENTER, 0, 0)
                                        toast.show()
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
                            queue.add(insertClientOrderRequest)
                        }
                    },
                    Response.ErrorListener {
                        val toast = Toast.makeText(
                            context,
                            "Cart contains more items than we have in stock",
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        (activity as MainActivity).cart = hashMapOf()
                        requireActivity().onBackPressed()
                    })
                queue.add(numInShopUpdateRequest)
            }
        }

        clientPreviousOrdersBtn.setOnClickListener {
            val bundle = bundleOf("account_id" to curr_client_id.toString())
            findNavController().navigate(R.id.action_accountFragment_to_ordersFragment, bundle)
        }

    }
}
