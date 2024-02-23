package ru.aapodomatko.testapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.aapodomatko.testapp.R
import ru.aapodomatko.testapp.models.Article

class GeneralAdapter: RecyclerView.Adapter<GeneralAdapter.NewsViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.category_rv_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.itemView.apply {
            val imageView = findViewById<ImageView>(R.id.news_image)
            val newsTitle = findViewById<TextView>(R.id.news_title)
            Glide.with(this)
                .load(item.image)
                .error(Glide.with(this).load(R.drawable.no_image_newww))
                .into(imageView)
            imageView.clipToOutline = true
            newsTitle.text = item.title

            setOnClickListener { onItemClickListener?.let { it(item) } }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    class NewsViewHolder(view: View): RecyclerView.ViewHolder(view)
}