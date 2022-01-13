package com.wama.libsignup.utility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.wama.libsignup.databinding.UploadPictureBottomsheetBinding
import com.wama.libsignup.utility.constant.Constant

class UploadMediaBottomSheet(
    private val mListener: BottomSheetListener,
) : BottomSheetDialogFragment(), View.OnClickListener {
    private var binding: UploadPictureBottomsheetBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = UploadPictureBottomsheetBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.cameraTxt.setOnClickListener(this)
        binding!!.galleryTxt.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v === binding!!.cameraTxt) {
            mListener.onItemSelected(Constant.OPEN_CAMERA)
        } else if (v === binding!!.galleryTxt) {
            mListener.onItemSelected(Constant.OPEN_GALLERY)
        }
    }

    interface BottomSheetListener {
        fun onItemSelected(requestCode: Int)
    }
}