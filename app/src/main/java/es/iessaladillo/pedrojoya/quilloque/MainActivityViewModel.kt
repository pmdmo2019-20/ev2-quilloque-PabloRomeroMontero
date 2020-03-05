package es.iessaladillo.pedrojoya.quilloque

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.quilloque.data.local.AppDatabase
import es.iessaladillo.pedrojoya.quilloque.data.local.entity.Call
import es.iessaladillo.pedrojoya.quilloque.data.local.entity.Contact
import es.iessaladillo.pedrojoya.quilloque.data.local.pojo.ContactoSugerido
import es.iessaladillo.pedrojoya.quilloque.data.local.pojo.LlamadaContacto

class MainActivityViewModel(private val repository: AppDatabase) : ViewModel() {

    val callList: LiveData<List<LlamadaContacto>> = repository.callDao.queryRecentCalls(20.toString())

    val contactList: LiveData<List<Contact>> = repository.contactDao.queryAllContact()

    var contactSugeridoList: LiveData<List<ContactoSugerido>> = repository.contactDao.querySugerenciasContacto("")

    var phoneNumberDial: MutableLiveData<String> = MutableLiveData("")


//    Fun CALL

    fun queryCall(id: Long): Call {
        return repository.callDao.querySearch(id.toString())
    }

    fun insertCall(call: Call) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute { repository.callDao.insertCall(call) }

    }

    fun deleteCall(call: Call) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute { repository.callDao.deleteCall(call) }
    }




    // FUN CONTACT

    fun queryContact(id: Long): Contact {
        return repository.contactDao.querySearch(id.toString())
    }

    fun insertContact(contact: Contact) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute { repository.contactDao.insertContact(contact) }

    }

    fun deleteContact(contact: Contact) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute { repository.contactDao.deleteContact(contact) }

    }

    fun cambiarListaSugerida(it: String) {
        contactSugeridoList = repository.contactDao.querySugerenciasContacto("%"+it+"%")
    }
}
