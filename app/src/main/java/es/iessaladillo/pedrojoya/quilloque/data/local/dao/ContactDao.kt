package es.iessaladillo.pedrojoya.quilloque.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import es.iessaladillo.pedrojoya.quilloque.data.local.entity.Contact
import es.iessaladillo.pedrojoya.quilloque.data.local.pojo.ContactoSugerido


@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    fun queryAllContact(): LiveData<List<Contact>>

    @Insert
    fun insertContact(call: Contact)

    @Delete
    fun deleteContact(call: Contact)

    @Query("Select * From contact where id = :id")
    fun querySearch(id: String) : Contact

    @Query("Select * From contact Where name like :nombre")
    fun queryContactsOf(nombre: String) : LiveData<List<Contact>>

    @Query(
        "SELECT name AS contactName, phoneNumber AS phoneNumber FROM Contact " +
                "WHERE phoneNumber like :phoneNumber UNION SELECT DISTINCT phoneNumber AS contactName, phoneNumber " +
                "AS phoneNumber FROM Call WHERE phoneNumber like :phoneNumber AND phoneNumber NOT IN " +
                "(SELECT phoneNumber FROM Contact)"
    )
    fun querySugerenciasContacto(phoneNumber: String): LiveData<List<ContactoSugerido>>
}