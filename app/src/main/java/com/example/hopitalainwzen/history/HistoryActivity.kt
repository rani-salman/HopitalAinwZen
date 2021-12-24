package com.example.hopitalainwzen.history

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hopitalainwzen.*


import com.example.hopitalainwzen.user.Appointment
import com.example.tictactoe.history.HistoryAdapter
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HistoryActivity : AppCompatActivity() {
    private var TAG = HistoryActivity::class.qualifiedName

    private lateinit var recyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter

    //intent.getStringExtra("type")

    private var query :Query ?= null

    val firestore = Firebase.firestore
//    val query = firestore.collection("Doctors").orderBy("type", Query.Direction.DESCENDING).limit(100)




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_activity)
        var ttype :String? = intent.getStringExtra("type")
        if (ttype=="patient") {
            query = firestore.collection("users").whereEqualTo("type", "doctor").orderBy("name", Query.Direction.DESCENDING)
        }
            else  {
               query = firestore.collection("users").whereEqualTo("type", "patient").orderBy("name", Query.Direction.DESCENDING)
            }

        historyAdapter = HistoryAdapter(query!!){
          //  var pat: User? = null
        if (ttype=="patient"){
            val intent = Intent(this, Appointment::class.java)
      //      intent.putExtra("pp",pat?.token)
            intent.putExtra("doctor", it)
            startActivity(intent)
        }
        else {
            val intent = Intent(this, DoctorResponse::class.java)
            intent.putExtra("patient", it)
            startActivity(intent)
        }

        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter.apply {
                notifyDataSetChanged()
            }

        }

    }

    public override fun onStart() {
        super.onStart()

        historyAdapter.startListening()
    }

    public override fun onStop() {
        super.onStop()
        historyAdapter.stopListening()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu")
        menuInflater.inflate(R.menu.mymenu, menu)
        //   associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager  //to get a reference
        val searchMenuItem = menu?.findItem(R.id.action_search) // to get a reference
        val searchView: SearchView = searchMenuItem.actionView as SearchView   //to get a reference
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))


        searchView.setOnSearchClickListener {

            Log.d(TAG, "OnSearchClickListener")
        }
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                Log.d(TAG, "MenuItemActionExpand")
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                Log.d(TAG, "MenuItemActionCollapse")
                return true
            }

        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "text search submit")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "text search text change")
                return true
            }

        })
        return true}
}


