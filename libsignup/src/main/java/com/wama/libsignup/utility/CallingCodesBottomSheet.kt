package com.wama.libsignup.utility

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wama.libsignup.R
import com.wama.libsignup.databinding.FragmentCallingCodesBinding
import com.wama.libsignup.utility.adapter.CallingCodesListAdapter
import com.wama.libsignup.utility.model.CallingCodeListItem

class CallingCodesBottomSheet(/*val mListener:BottomSheetListener, */val mContext: Context, private val callingCodeList: ArrayList<CallingCodeListItem>) : BottomSheetDialogFragment(),CallingCodesListAdapter.OnItemClick  {
    private var binding: FragmentCallingCodesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCallingCodesBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CallingCodesListAdapter(callingCodeList, this)
        val linearLayoutManager = LinearLayoutManager(mContext)
        binding!!.rvCallingCodes.layoutManager = linearLayoutManager
        binding!!.rvCallingCodes.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val code = callingCodeList[position].callingCode
      //  mListener.onItemSelected(code)
    }

    interface BottomSheetListener {
        fun onItemSelected(itemName: String?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
      /*  mListener = try {
            context as BottomSheetListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString()
                        + " must implement BottomSheetListener"
            )
        }*/
    }
}