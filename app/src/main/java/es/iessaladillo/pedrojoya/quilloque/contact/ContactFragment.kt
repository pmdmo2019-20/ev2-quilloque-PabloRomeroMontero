package es.iessaladillo.pedrojoya.quilloque.contact

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
import es.iessaladillo.pedrojoya.quilloque.dial.ContactFragmentAdapter
import es.iessaladillo.pedrojoya.quilloque.recent.RecentFragmentAdapter
import kotlinx.android.synthetic.main.contacts_fragment.*


class ContactFragment : Fragment(R.layout.contacts_fragment) {

    private val navController by lazy { findNavController() }

    private val listAdapter: ContactFragmentAdapter =
        ContactFragmentAdapter().apply {


            onClickListener = {

            }
            delete = {
                viewModel.deleteContact(viewModel.queryContact(getItemId(it)))
            }
            callLlamada = {
                viewModel.insertCall(
                    Call(
                        0,
                        CALL_TYPE_MADE,
                        viewModel.queryContact(getItemId(it)).phoneNumber,
                        "12-05-1995",
                        "12:45"
                    )
                )
                Toast.makeText(requireContext(), "llamada realizada", Toast.LENGTH_LONG).show()

            }
            callVideollamada = {

                viewModel.insertCall(
                    Call(
                        0,
                        CALL_TYPE_VIDEO,
                        viewModel.queryContact(getItemId(it)).phoneNumber,
                        "12-05-1995",
                        "12:45"
                    )
                )


                Toast.makeText(requireContext(), "Videollamada realizada", Toast.LENGTH_LONG).show()

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
        setHasOptionsMenu(true)
        initView()
        observe()
    }

    private fun observe() {
        viewModel.contactList.observe(this) {
            listAdapter.submitList(it)
            emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
        }
    }


    private fun initView() {
        setupRecyclerView()
        emptyView.setOnClickListener { navController.navigate(R.id.contactCreationFragmentDestination) }

        txtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (txtSearch.text.isEmpty()) {
                    observe()
                } else {
                    observeSearch()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contact_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun observeSearch() {
        viewModel.contactList.observeForever {  }
        viewModel.actualizarListaContact(txtSearch.text.toString()).observe(this) {
            listAdapter.submitList(it)
            emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.mnuAddContact -> {
                navController.navigate(R.id.contactCreationFragmentDestination)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    private fun setupRecyclerView() {
        lstContacts.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
    }


}