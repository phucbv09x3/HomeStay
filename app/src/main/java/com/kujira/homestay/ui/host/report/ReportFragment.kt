package com.kujira.homestay.ui.host.report

import android.widget.Toast
import com.kujira.homestay.R
import com.kujira.homestay.databinding.FragmentReportBinding
import com.kujira.homestay.ui.base.BaseFragment


class ReportFragment : BaseFragment<ReportViewModel, FragmentReportBinding>() {

    override fun createViewModel(): Class<ReportViewModel> {
        return ReportViewModel::class.java
    }

    override fun getResourceLayout(): Int {
        return R.layout.fragment_report
    }

    override fun initView() {
        val bundle = arguments
        val data = bundle?.getString("bundle")
        data?.let {
            viewModel.getClient(it)
        }
    }

    override fun bindViewModel() {
        viewModel.listener.observe(this, {
            if (it == 1) {
                Toast.makeText(context, getString(R.string.success), Toast.LENGTH_LONG).show()
            }
            if (it == 2) {
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show()
            }
            if (it == 1000) {
                Toast.makeText(context, getString(R.string.error_isEmpty_report), Toast.LENGTH_LONG).show()
            }
        })
    }
}