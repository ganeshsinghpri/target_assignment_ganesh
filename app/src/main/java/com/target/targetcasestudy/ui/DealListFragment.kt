package com.target.targetcasestudy.ui

import DealItemViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.DealItem
import com.target.targetcasestudy.interfaces.DealItemClickListener


class DealListFragment : Fragment(), DealItemClickListener {

    private lateinit var mAdapter: DealItemAdapter
    private lateinit var mViewModel: DealItemViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deal_list, container, false)

        mViewModel = ViewModelProvider(requireActivity()).get(DealItemViewModel::class.java)
        recyclerView = view.findViewById(R.id.recycler_view)

        mProgressBar = view.findViewById(R.id.progressBar)



        initializeObservers()

        return view
    }


    private fun initializeObservers() {

        mAdapter = DealItemAdapter(this)
        recyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }


        mViewModel.fetchDeals(false).observe(requireActivity(), Observer { kt ->
            mAdapter.setData(kt)
        })

        mViewModel.mShowProgressBar.observe(requireActivity(), Observer { bt ->
            if (bt) {
                mProgressBar.visibility = View.VISIBLE
            } else {
                mProgressBar.visibility = View.GONE

            }
        })


        mViewModel.mShowApiError.observe(requireActivity(), Observer {
            AlertDialog.Builder(requireActivity()).setMessage(it).show()
        })

        mViewModel.mShowNetworkError.observe(requireActivity(), Observer {
            AlertDialog.Builder(requireActivity()).setMessage(R.string.app_no_internet_msg).show()
        })
    }

    override fun onDealItemClickListener(data: DealItem) {


        mViewModel.getDealDetails(data.id, true).observe(requireActivity(), Observer { kt ->


            mViewModel.sendDealDetailItem(kt)


        })

        mViewModel.mNavigateToNextScreen.observe(requireActivity(), Observer { bt ->
            if (bt) {
                NavHostFragment.findNavController(this).navigate(R.id.listingFragment)
                mViewModel.mNavigateToNextScreen.value = false
            }
        })


    }


}
