package com.example.instagramclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagramclone.databinding.ItemForPostUserBinding
import com.example.instagramclone.model.Post

class UserPostAdapter(private var items: ArrayList<Post>,
                      private val context: Context) :
                      RecyclerView.Adapter<UserPostAdapter.MainHolder>() {


    inner class MainHolder(private val binding: ItemForPostUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val postImage = binding.postImage
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            ItemForPostUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {

        val item = items[position]
        Glide.with(context)
            .load(item.postImage)
            .into(holder.postImage)

    }
    override fun getItemCount(): Int {
        return items.size
    }
}


//        init {
//            checkBox.setOnCheckedChangeListener { _, isChecked ->
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val task = items[position]
//                    task.isActive = isChecked
//                }
//            }
//
//
//            checkBox.setOnClickListener {
//                val position = adapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    val task = items[position]
//                    task.isActive = checkBox.isChecked
//                    updateTaskView(task)
//
//
//                    CoroutineScope(Dispatchers.IO).launch {
//                        repository.update(task)
//                    }
//                }
//            }
//        }
//
//        fun bind(task: TaskEntity) {
//            taskText.text = task.task
//            checkBox.isChecked = task.isActive
//            updateTaskView(task)
//        }
//
//        private fun updateTaskView(task: TaskEntity) {
//
//
//            if (task.isActive) {
//                taskText.apply {
//                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                }
//            } else {
//                taskText.apply {
//                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
//                }
//            }
//        }
