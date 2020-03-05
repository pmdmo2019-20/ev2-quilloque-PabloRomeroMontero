package es.iessaladillo.pedrojoya.quilloque.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Contact",
    indices = [
        Index(
            name = "CONTACT_NAME_UNIQUE",
            value = ["name"],
            unique = true
        ),
        Index(
            name = "CONTACT_PHONENUMBER_UNIQUE",
            value = ["phoneNumber"],
            unique = true
        )]
)
data class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(
        name = "id"
    )
    val id: Long,
    @ColumnInfo(
        name = "name"
    )
    val name: String,
    @ColumnInfo(
        name = "phoneNumber"
    )
    val phoneNumber: String
)