package com.dominic.course.Adapter

import Courses
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dominic.course.R
import kotlinx.android.synthetic.main.item_course.view.*

class MyRecycleView(val context: Context, val list:ArrayList<Courses>, val rvClick: RvClick) : RecyclerView.Adapter<MyRecycleView.VH>() {

        inner class VH(var itemRv: View): RecyclerView.ViewHolder(itemRv){

            fun onBind(courses: Courses){
                itemRv.name.text = courses.CcyNm_UZC
                itemRv.course_ccy.text = courses.Ccy
                itemRv.price.text = courses.Rate
                itemRv.setOnClickListener {
                    rvClick.onClick(courses)
                }

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return  VH(LayoutInflater.from(parent.context).inflate(R.layout.item_course,parent,false))
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.onBind(list[position])
        }

        override fun getItemCount(): Int {
            return  list.size
        }
        interface RvClick{
            fun onClick(courses: Courses)

        }






}