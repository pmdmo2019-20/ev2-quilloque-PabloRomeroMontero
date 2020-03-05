package es.iessaladillo.pedrojoya.quilloque.contact_creation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import es.iessaladillo.pedrojoya.quilloque.MainActivityViewModel
import es.iessaladillo.pedrojoya.quilloque.MainActivityViewModelFactory
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.local.AppDatabase
import es.iessaladillo.pedrojoya.quilloque.data.local.entity.Contact
import kotlinx.android.synthetic.main.contact_creation_fragment.*

class ContactCreationFragment: Fragment(R.layout.contact_creation_fragment) {

    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            AppDatabase.getInstance(requireContext()),
            requireActivity().application
        )
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListener()
        initsViews()
    }

    private fun setupListener() {
        fabSave.setOnClickListener { saveContact() }
    }

    private fun saveContact() {
        if(txtPhoneNumber.text.isNotEmpty() &&  txtPhoneNumber.text.isNotEmpty()){
            viewModel.insertContact(Contact(0, txtName.text.toString(), txtPhoneNumber.text.toString()))
            Toast.makeText(requireContext(),"Contacto Creado", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(requireContext(),"Contacto No Creado, debe de tener todos los campos utilizados", Toast.LENGTH_LONG).show()
        }
    }

    private fun initsViews() {
        txtPhoneNumber.setText(viewModel.phoneNumberDial.value.toString())
    }
}