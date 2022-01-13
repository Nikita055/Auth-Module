package com.wama.libsignup.utility

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.wama.libsignup.R
import com.wama.libsignup.databinding.FragmentProfileBinding
import com.wama.libsignup.utility.constant.Constant
import com.wama.libsignup.utility.utility.FileUtils
import com.wama.libsignup.utility.utility.Utills
import java.io.File

class ProfileFragment: Fragment(), View.OnClickListener,
    UploadMediaBottomSheet.BottomSheetListener {
    private lateinit var binding: FragmentProfileBinding
    private var imagePath: String = ""
    private var isEmailUpdate = false
    private var mediaBottomSheet: UploadMediaBottomSheet? = null
//    private val fileUtils : FileUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.show()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imageView10,
            R.id.edit_imgVw -> {
                mediaBottomSheet = UploadMediaBottomSheet(this)
                mediaBottomSheet!!.show(requireActivity().supportFragmentManager, "UploadMedia")
            }
        }

    }


    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uri ->
                    imagePath =
                        FileUtils().getPath(requireActivity(), uri)!! /*getPath(requireActivity(), uri)*/
                    val imageFile = File(imagePath)
                    val size = FileUtils().getReadableFileSize(imageFile.length())
                    if (size!!.contains("MB")) {
                        val actualSize = size.substring(0, size.indexOf(" "))
                        if (actualSize.toFloat() > 10) {
                            try {
                                Utills().showAlertDialog(
                                    activity,
                                    "Alert",
                                    "Uploaded document should be less than 10 MB",
                                    getString(R.string.ok)
                                )
                            } catch (e: Exception) {
                                Log.e("TAG", e.toString())
                            }
                            return@registerForActivityResult
                        }
                    }
                   // updateProfileViewModel.uploadProfile(imagePath)
                }
            }
        }

    private var latestTmpUri: Uri? = null

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imagePath = FileUtils().getPath(requireActivity(), uri)!!
                val imageFile = File(imagePath)
                val size = FileUtils().getReadableFileSize(imageFile.length())
                if (size!!.contains("MB")) {
                    val actualSize = size.substring(0, size.indexOf(" "))
                    if (actualSize.toFloat() > 10) {
                        try {
                            Utills().showAlertDialog(
                                activity,
                                "Alert",
                                "Uploaded document should be less than 10 MB",
                                getString(R.string.ok)
                            )
                        } catch (e: Exception) {
                            Log.e("TAG", e.toString())
                        }
                        return@registerForActivityResult
                    }
                }
               // updateProfileViewModel.uploadProfile(imagePath)

            }
        }




    private fun getData() {

    }


    private fun initUi() {
        binding.editImgVw.setOnClickListener(this)
        binding.imageView10.setOnClickListener(this)
        binding.saveBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val contact = binding.contactNumberEt.text.toString()
            val address = binding.addressEt.text.toString()
            if (!binding.emailEt.validateEmail()) {
                return@setOnClickListener
            } else if (contact.length < 10) {
                binding.vehicleColorTel.error = "Please enter valid number "
                return@setOnClickListener
            } else if (address.isEmpty()) {
                binding.vehicleColorTel.error = "Please enter address"
                return@setOnClickListener
            } else {
                isEmailUpdate = true
              //  updateProfileViewModel.updateProfileApi(email, "91", contact, address)
            }
        }

    }



    private fun checkForPermission(isCamera: Boolean) {
        Dexter.withContext(requireActivity())
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report!!.areAllPermissionsGranted()) {
                        if (isCamera) {
                            takeImage()
                        } else {
                            selectImageFromGallery()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }

            }).check()
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    private fun getTmpFileUri(): Uri {
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tmpFile = File.createTempFile("tmp_image_file", ".png", storageDir).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(
            requireActivity(),
            requireActivity().applicationContext.packageName + ".utility.AppFileProvider",
            tmpFile
        )
    }

    private fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")
    override fun onItemSelected(requestCode: Int) {
        if (requestCode == Constant.OPEN_CAMERA) {
            checkForPermission(true)
        } else if (requestCode == Constant.OPEN_GALLERY) {
            checkForPermission(false)
        }
        mediaBottomSheet!!.dismiss()
    }

   /* private fun updateUser() {
        val user = WashXEmployeeSharedPreference.getQbUser()
        val email = WashXEmployeeSharedPreference.getUserDetails().email
        if (email != user?.login) {
            user!!.login = email
            user.password = Constant.USER_DEFAULT_PASSWORD
            ChatHelper.updateUser(user, object : QBEntityCallback<QBUser> {
                override fun onSuccess(qbUser: QBUser, bundle: Bundle?) {
                    WashXEmployeeSharedPreference.saveQbUser(qbUser)
                    disableScreenInteraction(requireActivity())
                }

                override fun onError(e: QBResponseException) {
                    e.message?.let { showSnackBar(binding.root, it) }
                    disableScreenInteraction(requireActivity())
                }
            })
        } else {
            disableScreenInteraction(requireActivity())
        }
    }*/

}