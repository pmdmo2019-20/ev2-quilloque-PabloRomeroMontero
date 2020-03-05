package es.iessaladillo.pedrojoya.quilloque.recent

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
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
import kotlinx.android.synthetic.main.recent_fragment.*

class RecentFragment : Fragment(R.layout.recent_fragment) {

//FALTA LA FECHA Y EL ACTUALIZAR BIEN LOS SUGERIDOS

    private val navController by lazy { findNavController() }

    private val listAdapter: RecentFragmentAdapter =
        RecentFragmentAdapter().apply {
            addContact = {
                viewModel.phoneNumberDial.value = viewModel.queryCall(getItemId(it)).phoneNumber
                navController.navigate(R.id.contactCreationFragmentDestination)
            }


            onClickListener = {

            }
            delete = {
                viewModel.deleteCall(viewModel.queryCall(getItemId(it)))
            }
            callLlamada = {
                viewModel.insertCall(
                    Call(
                        0,
                        CALL_TYPE_MADE,
                        viewModel.queryCall(getItemId(it)).phoneNumber,
                        "12-05-1995",
                        "12:45"
                    )
                )


            }
            callVideollamada = {

                viewModel.insertCall(
                    Call(
                        0,
                        CALL_TYPE_VIDEO,
                        viewModel.queryCall(getItemId(it)).phoneNumber,
                        "12-05-1995",
                        "12:45"
                    )
                )

            }
        }


    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            AppDatabase.getInstance(requireContext()),
            requireActivity().application
        )
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        observe()
    }

    private fun observe() {
        viewModel.callList.observe(this) {
            listAdapter.submitList(it)
            emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun initView() {
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        lstCalls.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
    }


}