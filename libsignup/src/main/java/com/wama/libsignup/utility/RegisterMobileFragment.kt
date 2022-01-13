package com.wama.libsignup.utility

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.wama.libsignup.R
import com.wama.libsignup.databinding.FragmentRegisterMobileNumberBinding
import com.wama.libsignup.utility.model.CallingCodeListItem
import java.util.*
import kotlin.collections.ArrayList

class RegisterMobileFragment : Fragment() {
    private lateinit var binding: FragmentRegisterMobileNumberBinding
    var navController: NavController?= null
    private var getNumber:String?=null
    private var codesBottomSheet: CallingCodesBottomSheet? = null
    val callingCodesList = ArrayList<CallingCodeListItem>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterMobileNumberBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initUi()
    }
    /*private val countryCodesList: Unit
        get() {
            val callingCodesJson = getJsonFromAssets(this, "callingCodes.json")
            val locale: Locale = Locale.getDefault()
            val localCountryName=locale.displayName
            try {
                val jsonObject = JSONObject(callingCodesJson!!)
                val jsonArray = jsonObject.getJSONArray("countries")
                for (i in 0 until jsonArray.length()) {
                    val callingCodeListItem = CallingCodeListItem()
                    val joInside = jsonArray.getJSONObject(i)
                    val code = joInside.getString("code")
                    val countryName = joInside.getString("name")
                    if (countryName==localCountryName){
                        binding.countryCodeLy.editText?.setText("$code")
                    }
                    callingCodeListItem.callingCode = code
                    callingCodeListItem.country = countryName
                    callingCodesList.add(callingCodeListItem)
                }
            } catch (e: JSONException) {
                Log.e("error", e.toString())
            }
        }
*/
    private fun initUi() {

        //adding some dummy data to the list
        callingCodesList.add(CallingCodeListItem("India", "+91"))
        callingCodesList.add(CallingCodeListItem("US", "+1"))
        callingCodesList.add(CallingCodeListItem("UK", "+2"))
        callingCodesList.add(CallingCodeListItem("Africa", "+9"))


        // countryCodesList
        binding.sendOtpBtn.setDisable()
        binding.sendOtpBtn.setOnClickListener {
            getNumber = binding.mobileEdit.text!!.trim().toString()
            if (getNumber!!.isNotEmpty() && getNumber!!.length >= 10) {
                binding.root.hideKeyboard()
             //   registerMobileViewModel.registerMobile(binding.countryCodeEdit.text.toString(),getNumber.toString())
            }
            navController!!.navigate(R.id.action_registerMobileFragment_to_otpFragment)

        }
        binding.countryCodeEdit.inputType = 0
        binding.countryCodeEdit.setOnClickListener {
            codesBottomSheet = CallingCodesBottomSheet(requireActivity(),callingCodesList)
            codesBottomSheet!!.show(requireActivity().supportFragmentManager, "CallingCodesList")

        }

        binding.mobileEdit.onSubmit {
            getNumber = binding.mobileEdit.text!!.toString().trim()
            //registerMobileViewModel.registerMobile(binding.countryCodeEdit.text.toString(),getNumber!!)

        }

        binding.mobileEdit.afterTextChanged {
            if (it.length>=10){
                binding.sendOtpBtn.setEnable()
                binding.sendOtpBtn.backgroundTintList = ContextCompat.getColorStateList(requireActivity(), R.color.primary_yellow)
            }else{
                binding.sendOtpBtn.backgroundTintList = ContextCompat.getColorStateList(requireActivity(), R.color.gray_20)
                binding.sendOtpBtn.setDisable()
            }
        }

       /* if (isFrom == "profile"){
            binding.backIv.makeVisible()
            binding.countryCodeLy.editText?.setText(intent?.getStringExtra("countryCode")!!)
            binding.mobileInputLy.editText?.setText(intent?.getStringExtra("phoneNumber")!!)
        }*/
        binding.backIv.setOnClickListener {

        }

    }
}