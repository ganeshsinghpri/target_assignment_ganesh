package com.target.targetcasestudy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.DealItem
import com.target.targetcasestudy.interfaces.DealItemClickListener


class DealItemAdapter(

    private val cellClickListener: DealItemClickListener
) : RecyclerView.Adapter<DealItemAdapter.DealItemViewHolder>() {

    private var mList: List<DealItem>? = listOf()


    fun setData(list: List<DealItem>) {
        mList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.deal_list_item, parent, false)
        return DealItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList!!.size
    }


    override fun onBindViewHolder(viewHolder: DealItemViewHolder, position: Int) {

        val item = mList?.get(position)

        item?.let {
            viewHolder.tvItemTitle.text = item.title

            viewHolder.tvItemAisle.text = item.aisle

            viewHolder.tvItemPrice.text = item.regular_price.display_string

            item.sale_price?.let {
                viewHolder.tvItemPrice.text = item.sale_price.display_string
            }


            loadImage(viewHolder.itemImage, item.imageUrl)


            viewHolder.itemView.setOnClickListener(View.OnClickListener {
                cellClickListener.onDealItemClickListener(item)

            })

        }


    }


    private fun loadImage(imageView: ImageView, imageUrl: String?) {
        Glide.with(imageView.context).load(imageUrl)
            .centerCrop()
            .thumbnail(0.5f)
            .placeholder(R.drawable.ic_launcher_background)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }


    class DealItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val tvItemTitle: TextView = itemView.findViewById(R.id.deal_list_item_title)
        val itemImage: ImageView = itemView.findViewById(R.id.deal_list_item_image_view)
        val tvItemPrice: TextView = itemView.findViewById(R.id.deal_list_item_price)
        val tvItemAisle: TextView = itemView.findViewById(R.id.deal_list_aisle)


    }

}



