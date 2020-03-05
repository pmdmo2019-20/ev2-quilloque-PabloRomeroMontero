package es.iessaladillo.pedrojoya.quilloque.dial

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.quilloque.MainActivityViewModel
import es.iessaladillo.pedrojoya.quilloque.MainActivityViewModelFactory
import es.iessaladillo.pedrojoya.quilloque.R
import es.iessaladillo.pedrojoya.quilloque.data.CALL_TYPE_MADE
import es.iessaladillo.pedrojoya.quilloque.data.CALL_TYPE_VIDEO
import es.iessaladillo.pedrojoya.quilloque.data.local.AppDatabase
import es.iessaladillo.pedrojoya.quilloque.data.local.entity.Call
import kotlinx.android.synthetic.main.dial_fragment.*

//FALTA LA FECHA Y EL ACTUALIZAR BIEN LOS SUGERIDOS

class DialFragment : Fragment(R.layout.dial_fragment) {

    private val listAdapter: DialFragmentAdapter =
        DialFragmentAdapter().apply {
            onClickListener = {
                deleteAllNumber()
                marcarNumero(getItemId(it).toString())
            }
        }

    private val navController by lazy { findNavController() }


    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            AppDatabase.getInstance(requireContext()),
            requireActivity().application
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.phoneNumberDial.value=""
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observe()
    }

    private fun observe() {
        viewModel.phoneNumberDial.observe(this) {
            viewModel.cambiarListaSugerida(it)
            imgBackspace.visibility = if (it.isNotEmpty()) View.VISIBLE else View.INVISIBLE
            imgVideo.visibility = if (it.isNotEmpty()) View.VISIBLE else View.INVISIBLE
            }
        viewModel.contactSugeridoList.observe(this) { listAdapter.submitList(it)
            lblCreateContact.visibility = if (it.isEmpty()&& lblNumber.text.isNotEmpty()) View.VISIBLE else View.INVISIBLE}
    }

    private fun initView() {
        setupRecyclerView()
        setUpOnclickListener()

    }

    private fun setupRecyclerView() {
        lstSuggestions.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
    }

    private fun setUpOnclickListener() {
        lblOne.setOnClickListener { marcarNumero("1") }
        lblTwo.setOnClickListener { marcarNumero("2") }
        lblThree.setOnClickListener { marcarNumero("3") }
        lblFour.setOnClickListener { marcarNumero("4") }
        lblFive.setOnClickListener { marcarNumero("5") }
        lblSix.setOnClickListener { marcarNumero("6") }
        lblSeven.setOnClickListener { marcarNumero("7") }
        lblEight.setOnClickListener { marcarNumero("8") }
        lblNine.setOnClickListener { marcarNumero("9") }
        lblAstherisc.setOnClickListener { marcarNumero("*") }
        lblZero.setOnClickListener { marcarNumero("0") }
        lblPound.setOnClickListener { marcarNumero("#") }

//        --

        imgVideo.setOnClickListener { saveCall(false) }
        fabCall.setOnClickListener { saveCall(true) }
        imgBackspace.setOnClickListener { deleteNumber() }
        imgBackspace.setOnLongClickListener { deleteAllNumber() }



        lblCreateContact.setOnClickListener({ navController.navigate(R.id.contactCreationFragmentDestination) })
    }

    private fun deleteAllNumber(): Boolean {
        lblNumber.text = ""
        viewModel.phoneNumberDial.value = ""
        return true
    }

    private fun deleteNumber() {
        val aux: String =
            lblNumber.text.toString().substring(0, lblNumber.text.toString().length - 1)
        lblNumber.text = aux
        viewModel.phoneNumberDial.value = aux
    }

    @SuppressLint("SimpleDateFormat")
    private fun saveCall(type: Boolean) {
        if (lblNumber.text.isNotEmpty()) {
            val tipoLlamada: String = if (type) CALL_TYPE_MADE else CALL_TYPE_VIDEO
            viewModel.insertCall(
                Call(
                    0,
                    tipoLlamada,
                    lblNumber.text.toString(),"12-05-1995","12:45")
                )
            Toast.makeText(requireContext(),if (type) getString(R.string.dial_call_made) else getString(R.string.dial_videocall_made),Toast.LENGTH_LONG).show()
        }
    }

    private fun marcarNumero(numero: String) {
        val aux: String = lblNumber.text.toString()
        lblNumber.text = aux.plus(numero)
        viewModel.phoneNumberDial.value = aux.plus(numero)
    }
}