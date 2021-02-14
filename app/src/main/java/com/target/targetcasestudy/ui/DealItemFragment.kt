package com.target.targetcasestudy.ui

import DealItemViewModel
import android.graphics.Paint
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.DealItem


class DealItemFragment : Fragment() {

    private lateinit var mViewModel: DealItemViewModel
    private lateinit var mTitleTv: TextView
    private lateinit var mProductImageView: ImageView
    private lateinit var mDescriptionTv: TextView
    private lateinit var mSalePriceTv: TextView
    private lateinit var mOriginalPriceTv: TextView
    private lateinit var mRegPriceHintTv: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_deal_item, container, false)
        mTitleTv = view.findViewById(R.id.product_title)
        mProductImageView = view.findViewById(R.id.product_image)
        mDescriptionTv = view.findViewById(R.id.product_desc)
        mSalePriceTv = view.findViewById(R.id.product_sale_price)
        mOriginalPriceTv = view.findViewById(R.id.product_original_price)
        mRegPriceHintTv = view.findViewById(R.id.reg_price_hint);

        mViewModel = ViewModelProvider(requireActivity()).get(DealItemViewModel::class.java)


        return view
    }

    override fun onResume() {
        super.onResume()
        val model = ViewModelProvider(requireActivity()).get(DealItemViewModel::class.java)
        model.mDealDetailItem.observe(viewLifecycleOwner, Observer {
            setData(it)
        })

    }


    private fun setData(dealItem: DealItem) {
        mTitleTv.text = dealItem.title
        mDescriptionTv.text = dealItem.description

        if (dealItem.sale_price != null) {
            mSalePriceTv.text = dealItem.sale_price.display_string

            mRegPriceHintTv.text = "Reg. "
            mOriginalPriceTv.text = dealItem.regular_price.display_string

            mOriginalPriceTv.paintFlags = mOriginalPriceTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        } else {
            mRegPriceHintTv.text = ""
            mSalePriceTv.text = dealItem.regular_price.display_string
        }

        loadImage(mProductImageView, dealItem.imageUrl)

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


}
