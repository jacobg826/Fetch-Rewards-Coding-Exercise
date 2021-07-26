package com.example.fetchrewardstest

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONException
import java.net.MalformedURLException
import java.net.URL


class MainActivity : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ignore needing to network on separate thread
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        // get url with JSON data
        val url: URL? = try {
            URL("https://fetch-hiring.s3.amazonaws.com/hiring.json")
        }catch (e: MalformedURLException){
            Log.d("No URL Exception", e.toString())
            null
        }

        // read JsonData into a list of items then sort items
        val itemList: MutableList<Item> = parseJson(url?.readText())
        itemList.sortWith(
            compareBy<Item> { it.listId }.thenBy { it.name?.substringAfter(" ")?.toInt() }
        )

        itemAdapter = ItemAdapter(itemList)
        val listItems: RecyclerView = findViewById(R.id.list)
        listItems.adapter = itemAdapter
        listItems.layoutManager = LinearLayoutManager(applicationContext)

    }

    private fun parseJson (data: String?) : MutableList<Item> {
        val items = mutableListOf<Item>()
        try {
            val itemArray = JSONArray(data)
            for (i in 0 until itemArray.length()) {
                val jsonItem = itemArray.getJSONObject(i)
                val id = jsonItem.getInt("id")
                val listId = jsonItem.getInt("listId")
                val name: String? = jsonItem.getString("name")
                if (!name.isNullOrEmpty() && name != "null") {
                    val newItem = Item(id, listId, name)
                    items.add(newItem)
                }
            }
        } catch (e: JSONException) {
            Log.d("Parse Json Exception", e.toString())
        }

        return items
    }
}