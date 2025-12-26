package ru.dev.sergey

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.core.net.toUri

class Adapter(
    private val items: List<Offer>,
    private val context: Context
): RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, context)
    }

    override fun getItemCount(): Int {
        println("getItemCount: ${items.size}")
        return items.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_title)
        private val description: TextView = itemView.findViewById(R.id.tv_description)
        private val buttonGo: Button = itemView.findViewById(R.id.btn_go)

        fun bind(item: Offer, context: Context) {
            title.text = item.title
            description.text = item.description
            buttonGo.text = item.textButton

            buttonGo.setOnClickListener {

                try {
                    val intent = Intent(context, Browser::class.java)
                    intent.putExtra("url", item.url)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Не удалось открыть ссылку: ${e.toString()}", Toast.LENGTH_SHORT).show()
                    Log.e("ERROR", e.toString())
                }

//                try {
//                    val intent = Intent(Intent.ACTION_VIEW, item.url.toUri())
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    context.startActivity(intent)
//                } catch (e: Exception) {
//                    Toast.makeText(context, "Не удалось открыть ссылку: ${e.toString()}", Toast.LENGTH_SHORT).show()
//                }

            }

        }
    }
}
