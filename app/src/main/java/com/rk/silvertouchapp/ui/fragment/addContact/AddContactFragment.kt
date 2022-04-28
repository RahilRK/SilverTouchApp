package com.rk.silvertouchapp.ui.fragment.addContact

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rk.silvertouchapp.adapter.CategorySpinnerAdapter
import com.rk.silvertouchapp.databinding.FragmentAddContactBinding
import com.rk.silvertouchapp.model.Category
import com.rk.silvertouchapp.model.Contact
import com.rk.silvertouchapp.util.Application
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository
import de.hdodenhof.circleimageview.CircleImageView
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import java.util.regex.Pattern


class AddContactFragment : Fragment() {

    var TAG = this.javaClass.simpleName
    private lateinit var activity: Context

    lateinit var binding : FragmentAddContactBinding
    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository
    lateinit var viewModel: AddContactFragVM

    val profileImage : CircleImageView get() = binding.profileImage
    val firstNameEd : TextInputEditText get() = binding.firstNameEd
    val lastNameEd : TextInputEditText get() = binding.lastNameEd
    val mobileNoEd : TextInputEditText get() = binding.mobileNoEd
    val emailIdEd : TextInputEditText get() = binding.emailIdEd
    val categorySpinner : Spinner get() = binding.categorySpinner
    val saveCategorybt : Button get() = binding.saveCategorybt

    private var arrayList = arrayListOf<Category>()
    lateinit var adapter: CategorySpinnerAdapter

    var selectedimagesArrayList = java.util.ArrayList<Uri>()
    var categoryId = 0
    var categoryName = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireContext()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddContactBinding.inflate(layoutInflater,container,false)

        init()
        onClick()
        observeData()

        return binding.root
    }

    private fun init() {
        globalClass = (requireActivity().application as Application).globalClass
        repository = (requireActivity().application as Application).repository
        viewModel = ViewModelProvider(this, AddContactFragVMFactory(repository))
            .get(AddContactFragVM::class.java)
    }

    private fun onClick() {

        profileImage.setOnClickListener {
            requestStoragePermission()
        }

        saveCategorybt.setOnClickListener {

            if(isValidate()) {
                viewModel.addContact(
                    Contact(
                        profileImage = selectedimagesArrayList[0].toString(),
                        firstName = firstNameEd.text.toString(),
                        lastName = lastNameEd.text.toString(),
                        mobileNumber = mobileNoEd.text.toString(),
                        emailId = emailIdEd.text.toString(),
                        categoryId = categoryId,
                        categoryName = categoryName,
                    ),
                )
                globalClass.toastlong("Contact added successfully")
                getActivity()?.onBackPressed()
            }
        }
    }

    private fun isValidate():Boolean {

        if(firstNameEd.text.isNullOrEmpty()) {
            globalClass.toastlong("First name should not be empty")
            return false
        }
        else if(lastNameEd.text.isNullOrEmpty()) {
            globalClass.toastlong("Last name should not be empty")
            return false
        }
        else if(mobileNoEd.text!!.length != 10) {
            globalClass.toastlong("Invalid mobile number")
            return false
        }
        else if(emailIdEd.text!!.isEmpty() || !isValidEmail(emailIdEd.text.toString())) {
            globalClass.toastlong("Invalid Email Id")
            return false
        }

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun observeData() {

        viewModel.categoryList.observe(viewLifecycleOwner) { list ->

            arrayList = list as ArrayList<Category>

            if(arrayList.isNotEmpty()) {
                adapter = CategorySpinnerAdapter(activity, arrayList)
                categorySpinner.setAdapter(adapter)
                categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                        val model = arrayList.get(position)
                        categoryId = model.id.toInt()
                        categoryName = model.categoryName
                    }
                }
            }
            else {
                globalClass.toastlong("Please add some category")
                getActivity()?.onBackPressed()
            }
        }
    }

    fun requestStoragePermission() {

        Dexter.withActivity(requireActivity())
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        openImagePicker()
                    }
                    if (report.deniedPermissionResponses.size > 0) {
                        globalClass.log(TAG, "Storage permission Denied")
                        globalClass.toastlong("Storage permission required to choose image")
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .withErrorListener { error ->
                globalClass.log(TAG + "__requestStoragePermission", error.toString())
            }
            .onSameThread()
            .check()
    }

    fun openImagePicker() {
        selectedimagesArrayList.clear()
        FilePickerBuilder.instance
            .setMaxCount(1)
            .enableCameraSupport(false)
            .setSelectedFiles(selectedimagesArrayList)
//            .setActivityTheme(R.style.LibAppTheme)
            .pickPhoto(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                selectedimagesArrayList.clear()
                data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)
                    ?.let { selectedimagesArrayList.addAll(it) }

                globalClass.log(TAG, "selectedImages: ${selectedimagesArrayList.size}")
                if(selectedimagesArrayList.isNotEmpty()) {

                    profileImage.setImageURI(selectedimagesArrayList[0])
                }
                else {
                    globalClass.toastlong("Unable to load image")
                }
            }
        }
    }
}