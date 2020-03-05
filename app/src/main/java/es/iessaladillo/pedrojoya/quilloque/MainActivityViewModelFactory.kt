package es.iessaladillo.pedrojoya.quilloque


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.quilloque.data.local.AppDatabase

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(
    val repository: AppDatabase,
    application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Must provide ScheduleActivityViewModel class")
    }

}