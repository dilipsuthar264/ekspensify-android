package com.ekspensify.app.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ekspensify.app.room.dao.PendingTransactionDao
import com.ekspensify.app.room.model.PendingTransactionModel


@Database(
    entities = [PendingTransactionModel::class],
    version = 2,
    exportSchema = false
)
abstract class EkspensifyDatabase : RoomDatabase() {

    abstract fun pendingTransactionDao(): PendingTransactionDao

    companion object {

        @Volatile
        private var INSTANCE: EkspensifyDatabase? = null

        fun getDataBase(context: Context): EkspensifyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = EkspensifyDatabase::class.java,
                    name = "ekspensify_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}