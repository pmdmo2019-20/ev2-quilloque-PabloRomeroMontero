package es.iessaladillo.pedrojoya.quilloque.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import es.iessaladillo.pedrojoya.quilloque.data.local.entity.Call
import es.iessaladillo.pedrojoya.quilloque.data.local.pojo.LlamadaContacto


@Dao
interface CallDao {


    @Query("SELECT * FROM call")
    fun queryAllCall() : LiveData<List<Call>>

    @Query("Select * From call where id = :id")
    fun querySearch(id: String) : Call

    @Query("SELECT C.id AS callId, C.phoneNumber AS phoneNumber, C.type AS type," +
            "   C.date AS date, C.time AS time, T.id AS contactId, T.name AS contactName " +
            "   FROM Call AS C LEFT JOIN Contact AS T ON C.phoneNumber = T.phoneNumber " +
            "   ORDER BY C.id DESC LIMIT :limit")
    fun queryRecentCalls(limit: String) : LiveData<List<LlamadaContacto>>


    @Insert
    fun insertCall(call: Call)

    @Delete
    fun deleteCall(call: Call)
}
