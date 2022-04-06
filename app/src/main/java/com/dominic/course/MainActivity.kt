package com.dominic.course

import Courses
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.dominic.course.Adapter.MyRecycleView
import com.dominic.course.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.item_info.view.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var list:ArrayList<Courses>
    lateinit var networkHelper: NetworkHelper
    lateinit var requestQueue: RequestQueue
    lateinit var myRecycleView: MyRecycleView
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        list = ArrayList()
        recyclerView = binding.root.findViewById(R.id.recycle_view)
        networkHelper = NetworkHelper(this)
        requestQueue = Volley.newRequestQueue(this)
         check()




    }

    private fun check() {
        if (networkHelper.isNetworkConnected()){
            loadData("http://cbu.uz/uzc/arkhiv-kursov-valyut/json/")
        }else{
            Toast.makeText(this, "Internet yo`q", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData(url:String) {
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,url,null,object : Response.Listener<JSONArray>{
            override fun onResponse(response: JSONArray?) {
                val str = response.toString()
                val type = object : TypeToken<ArrayList<Courses>>(){}.type
                list = Gson().fromJson(str,type)
                myRecycleView = MyRecycleView(this@MainActivity,list,object : MyRecycleView.RvClick{
                    override fun onClick(courses: Courses) {
                       val mysheet = BottomSheetDialog(this@MainActivity)
                        val item = LayoutInflater.from(this@MainActivity).inflate(R.layout.item_info,null,false)
                        mysheet.setContentView(item)
                        mysheet.show()
                        item.info_name.text = courses.CcyNm_UZC
                        item.info_code.text = "Code: ${courses.Code}"
                        item.info_date.text = "${courses.Date} sanasi bo`yicha kurs: "
                        item.info_diff.text = "Diff: ${courses.Diff}"
                        item.info_naminal.text = "Naminal: ${courses.Nominal}"
                        item.info_ccy.text = "Ccy: ${courses.Ccy}"
                        item.info_rate.text = courses.Rate
                    }
                })
                myRecycleView.onAttachedToRecyclerView(binding.recycleView)
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
               recyclerView.adapter = myRecycleView

            }
        },Response.ErrorListener {
            Toast.makeText(this, "Server hatoligi !", Toast.LENGTH_SHORT).show()
        })
        requestQueue.add(jsonArrayRequest)
    }
    private fun translationToUzb(){

    }
    private fun trantlationToRus(){

    }
    private fun translationToUzbKrl(){

    }
}