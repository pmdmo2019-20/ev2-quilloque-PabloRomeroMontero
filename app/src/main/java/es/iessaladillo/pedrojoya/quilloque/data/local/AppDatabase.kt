package es.iessaladillo.pedrojoya.quilloque.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import es.iessaladillo.pedrojoya.quilloque.data.local.dao.CallDao
import es.iessaladillo.pedrojoya.quilloque.data.local.dao.ContactDao
import es.iessaladillo.pedrojoya.quilloque.data.local.entity.Call
import es.iessaladillo.pedrojoya.quilloque.data.local.entity.Contact

@Database(
    entities = [
        Contact::class,
        Call::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val contactDao: ContactDao
    abstract val callDao: CallDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "app_database"
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
