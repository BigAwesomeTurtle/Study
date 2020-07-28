package org.BigAwesomeTurtle.bar_db_app.model

import android.app.Activity
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL

class ServerImpl(private val act:Activity?) {

    private lateinit var backgroundThread: Thread
    private lateinit var url: String
    private lateinit var json: JSONObject
    private lateinit var callback: (resp: String) -> Unit

    private fun db_request(
        curr_url: String,
        params: Map<String, Any>,
        currCallback: (resp: String) -> Unit
    ) {
        url = curr_url
        json = JSONObject()
        callback = currCallback
        for (elem in params) {
            json.put(elem.key, elem.value)
        }

        backgroundThread = Thread(getInfoRunnable)
        backgroundThread.start()
    }

    fun db_get(params: Map<String, Any>, currCallback: (resp: String) -> Unit) {
         db_request("HTTP://192.168.0.101:3222/get", params, currCallback)
    }

    fun db_insert(params: Map<String, Any>, currCallback: (resp: String) -> Unit) {
         db_request("HTTP://192.168.0.101:3222/insert", params, currCallback)
    }

    fun db_remove(params: Map<String, Any>, currCallback: (resp: String) -> Unit) {
         db_request("HTTP://192.168.0.101:3222/delete", params, currCallback)
    }

    fun db_update(params: Map<String, Any>, currCallback: (resp: String) -> Unit) {
         db_request("HTTP://192.168.0.101:3222/update", params, currCallback)
    }

    private val getInfoRunnable: Runnable = Runnable {
        makeRequestWithCallback(url, json.toString(), 10000, "POST", callback)
    }


    private fun makeRequestWithCallback(
        url: String?,
        json: String?,
        timeout: Int,
        method: String?,
        currCallback: (resp: String) -> Unit
    ): Boolean {
        try {

            var connection: HttpURLConnection?
            val u = URL(url)
            connection = u.openConnection() as HttpURLConnection
            connection.requestMethod = method

            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Accept", "application/json")
            connection.allowUserInteraction = false
            connection.connectTimeout = timeout
            connection.readTimeout = timeout
            if (json != null) {
                connection.setRequestProperty(
                    "Content-length",
                    json.toByteArray().size.toString() + ""
                )
                connection.doInput = true
                connection.doOutput = true
                connection.useCaches = false

                val outputStream = connection.outputStream
                outputStream.write(json.toByteArray(charset("UTF-8")))
                outputStream.close()
            }

            connection.connect()
            val status = connection.responseCode
            Log.i("HTTP Client", "HTTP status code : $status")
            when (status) {
                200, 201 -> {
                    val currConn =
                        if (status == 400) connection.errorStream else connection.inputStream
                    val bufferedReader =
                        BufferedReader(InputStreamReader(currConn))
                    val sb = java.lang.StringBuilder()
                    var line = bufferedReader.readLine()
                    while (line != null) {
                        sb.append(line.trimIndent())
                        line = bufferedReader.readLine()
                    }
                    bufferedReader.close()
                    Log.i("HTTP Client", "Received String : $sb")
                    act?.runOnUiThread(Runnable {
                        currCallback(sb.toString())
                    })
                    return true
                }
            }

        } catch (e: SocketTimeoutException) {
            Log.e("HTTP Client", "Socket timeout")
        }
        catch (e: ConnectException){
            Log.e("HTTP Client", "Failed to Connect")
        }
        act?.runOnUiThread(Runnable {
            val toast = Toast.makeText(
                act.applicationContext,
                "Что-то пошло не так при обращении к серверу",
                Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        })
        return false
    }

}