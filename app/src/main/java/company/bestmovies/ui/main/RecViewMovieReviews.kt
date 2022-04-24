package company.bestmovies.ui.main

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import company.bestmovies.data.local.entity.MovieReviewEntity
import company.bestmovies.databinding.ItemEmptyBinding
import company.bestmovies.databinding.ItemReviewBinding

class RecViewMovieReviews : RecyclerView.Adapter<RecViewMovieReviews.ItemHolder>() {

    private var items: MutableList<MovieReviewEntity> =
        arrayListOf()

    fun setData(items: List<MovieReviewEntity>) {
        this.items = items as MutableList<MovieReviewEntity>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return when (viewType) {
            ITEM_TYPE_EMPTY -> {
                val itemEmptyBinding =
                    ItemEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemEmptyHolder(itemEmptyBinding)
            }
            ITEM_TYPE_LOAD -> {
                val itemReviewBinding =
                    ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemReviewHolder(itemReviewBinding)
            }
            else -> {
                val itemEmptyBinding =
                    ItemEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemEmptyHolder(itemEmptyBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        when (holder) {
            is ItemReviewHolder -> {
                holder.bind(items[position])
            }
            is ItemEmptyHolder -> {
                holder.bind("Упс, рецензий не нашлось")
            }
        }
    }

    override fun getItemCount(): Int {
        return if (items.isNotEmpty()) items.size else 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.isEmpty()) {
            ITEM_TYPE_EMPTY
        } else {
            ITEM_TYPE_LOAD
        }
    }

    companion object {
        const val ITEM_TYPE_EMPTY = 1
        const val ITEM_TYPE_LOAD = 2
    }

    open inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ItemReviewHolder(private val itemBinding: ItemReviewBinding) :
        ItemHolder(itemBinding.root) {
        fun bind(review: MovieReviewEntity) {
            with(itemBinding) {
                this.reviewTitle.text = review.headline
                this.reviewDescription.text = review.summary_short
                Picasso.get().load(review.multimedia_src).into(this.reviewPoster)
            }
        }
    }

    inner class ItemEmptyHolder(private val itemBinding: ItemEmptyBinding) :
        ItemHolder(itemBinding.root) {
        fun bind(data: String) {
            with(itemBinding) {
                this.emptyDesc.text = data
            }
        }
    }

    internal class ItemDecoration(private val mPaddingVertical: Int, private val mPaddingHorizontal: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.left = mPaddingHorizontal
            outRect.right = mPaddingHorizontal
            outRect.bottom = mPaddingVertical
            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = mPaddingVertical
            }
        }
    }
}