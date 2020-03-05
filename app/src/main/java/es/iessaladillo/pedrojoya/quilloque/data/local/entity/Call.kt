package es.iessaladillo.pedrojoya.quilloque.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "Call"
)
data class Call(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(
        name = "id"
    )
    val id: Long,
    @ColumnInfo(
        name = "type")
    val type: String,
    @ColumnInfo(
        name = "phoneNumber"
    )
    val phoneNumber: String,
    @ColumnInfo(
        name = "date" )
    val date: String,
    @ColumnInfo(
        name = "time")
    val time: String
)