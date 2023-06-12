package com.example.postit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<DataClass>
    lateinit var imageList: Array<Int>
    lateinit var titleList: Array<String>
    private lateinit var myAdapter : AdapterClass
    private lateinit var searchView: SearchView
    private lateinit var searchList: ArrayList<DataClass>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        imageList = arrayOf(
            R.drawable.ic_list,
            R.drawable.ic_checkbox,
            R.drawable.ic_image,
            R.drawable.ic_date,
            R.drawable.ic_text,
            R.drawable.ic_rating
        )

        titleList = arrayOf(
            getString(R.string.chores),
            getString(R.string.achievements),
            getString(R.string.images),
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
                    Toast.makeText(activity, "Pulsado chores", Toast.LENGTH_SHORT).show()
                    val choresIntent = Intent(activity, ChoresActivity::class.java)
                    activity?.startActivity(choresIntent)
                }
                getString(R.string.achievements) -> {
                    Toast.makeText(activity, "Pulsado achievements", Toast.LENGTH_SHORT).show()
                }
                getString(R.string.images) -> {
                    Toast.makeText(activity, "Pulsado images", Toast.LENGTH_SHORT).show()
                }
                getString(R.string.dates) -> {
                    Toast.makeText(activity, "Pulsado dates", Toast.LENGTH_SHORT).show()
                }
                getString(R.string.write) -> {
                    Toast.makeText(activity, "Pulsado write", Toast.LENGTH_SHORT).show()
                }
                getString(R.string.rating) -> {
                    Toast.makeText(activity, "Pulsado ratings", Toast.LENGTH_SHORT).show()
                    val ratingsIntent = Intent(activity, RatingActivity::class.java)
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

