package com.example.postit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<DataClass>
    lateinit var imageList: Array<Int>
    lateinit var titleList: Array<String>
    private lateinit var myAdapter : AdapterClass
    private lateinit var searchView: SearchView
    private lateinit var searchList: ArrayList<DataClass>
    private var username : String? = null

    private val userViewModel : UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        imageList = arrayOf(
            R.drawable.ic_list,
            R.drawable.ic_date,
            R.drawable.ic_text,
            R.drawable.ic_rating
        )

        titleList = arrayOf(
            getString(R.string.chores),
            getString(R.string.dates),
            getString(R.string.write),
            getString(R.string.rating)
        )

        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.search)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        dataList = arrayListOf<DataClass>()
        searchList = arrayListOf<DataClass>()
        getData()

        username = userViewModel.getUsername()
        val navigationView = activity?.findViewById<NavigationView>(R.id.nav_view)
        val header = navigationView?.getHeaderView(0)
        var headerUsername = header?.findViewById<TextView>(R.id.header_user)
        if (headerUsername != null) {
            headerUsername.text = userViewModel.getUsername()
        }

        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()){
                    dataList.forEach{
                        if (it.dataTitle.toLowerCase(Locale.getDefault()).contains(searchText)){
                            searchList.add(it)
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()
                } else{
                    searchList.clear()
                    searchList.addAll(dataList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }
        })

        myAdapter = AdapterClass(searchList)
        recyclerView.adapter = myAdapter

        myAdapter.onItemClick = {
            when(it.dataTitle){
                getString(R.string.chores) -> {
                    val choresIntent = Intent(activity, ChoresActivity::class.java)
                    choresIntent.putExtra("username", userViewModel.getUsername())
                    activity?.startActivity(choresIntent)
                }
                getString(R.string.dates) -> {
                    val dateIntent = Intent(activity, DateActivity::class.java)
                    dateIntent.putExtra("username", userViewModel.getUsername())
                    activity?.startActivity(dateIntent)
                }
                getString(R.string.write) -> {
                    val notesIntent = Intent(activity, NotesActivity::class.java)
                    notesIntent.putExtra("username", userViewModel.getUsername())
                    activity?.startActivity(notesIntent)
                }
                getString(R.string.rating) -> {
                    val ratingsIntent = Intent(activity, RatingActivity::class.java)
                    ratingsIntent.putExtra("username", userViewModel.getUsername())
                    activity?.startActivity(ratingsIntent)
                }
            }
        }
        return view
    }

    private fun getData(){
        for (i in imageList.indices){
            val dataClass = DataClass(imageList[i], titleList[i])
            dataList.add(dataClass)
        }

        searchList.addAll(dataList)
        recyclerView.adapter = AdapterClass(searchList)
    }

}

