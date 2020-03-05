package es.iessaladillo.pedrojoya.quilloque.data.local.pojo

import androidx.annotation.Nullable
import androidx.room.ColumnInfo

data class LlamadaContacto(
    @ColumnInfo(name = "callId")
    val callId: Long,
    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "contactId")
    @Nullable
    val contactId: String?,
    @ColumnInfo(name = "contactName")
    @Nullable
    val contactName: String?
)

data class ContactoSugerido(

    @ColumnInfo(name = "contactName")
    @Nullable
    val contactName: String,
    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String
)


