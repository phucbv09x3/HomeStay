package com.kujira.homestay.ui.client.travelAll

import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.kujira.homestay.R
import com.kujira.homestay.data.model.Provinces
import com.kujira.homestay.databinding.FragmentTravelAllBinding
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.utils.Constants

class TravelAllFragment : BaseFragment<TravelAllViewModel, FragmentTravelAllBinding>() {
    override fun createViewModel(): Class<TravelAllViewModel> {
        return TravelAllViewModel::class.java
    }

    override fun getResourceLayout(): Int = R.layout.fragment_travel_all

    override fun initView() {
        with(dataBinding.rcyListTravelAll) {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = TravelAdapter(mutableListOf())
        }
        search()
    }

    override fun bindViewModel() {

        val bundle = arguments
        if (bundle == null) {
            viewModel.getListTravelAll()
            viewModel.listTravels.observe(this, {
                (dataBinding.rcyListTravelAll.adapter as TravelAdapter).setList(it)
            })
        } else {
            val data = bundle?.getParcelable<Provinces>(Constants.KEY_ID) as Provinces
            data?.let { data ->
                dataBinding.tvShowProvinces.text = data.name
                dataBinding.tvShowProvinces.visibility = View.VISIBLE
                viewModel.getListTravelId(data.name)
                viewModel.listTravels.observe(this, {
                    (dataBinding.rcyListTravelAll.adapter as TravelAdapter).setList(it)
                })
            }
        }
//        viewModel.getListTravelAll()
//        viewModel.listTravels.observe(this, {
//            Log.d("list", "$it")
//            (dataBinding.rcyListTravelAll.adapter as TravelAdapter).setList(it)
//        })

        viewModel.listenerBack.observe(this, {
            navigators.navigateUp()
        })
    }

    private fun search() {
        dataBinding.svNameProvinceList.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!TextUtils.isEmpty(query?.trim())) {
                    viewModel.searchTravel(query)
                    viewModel.listTravels.observe(this@TravelAllFragment, {
                        Log.d("list", "$it")
                        (dataBinding.rcyListTravelAll.adapter as TravelAdapter).setList(it)
                    })


                } else {
                   viewModel.getListTravelAll()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!TextUtils.isEmpty(newText?.trim())) {
                    viewModel.searchTravel(newText)
                    viewModel.listTravels.observe(this@TravelAllFragment, {
                        Log.d("list", "$it")
                        (dataBinding.rcyListTravelAll.adapter as TravelAdapter).setList(it)
                    })
                } else {
                    viewModel.getListTravelAll()
                }
                return false
            }

        })
    }
}