package com.ekspensify.app.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ekspensify.app.room.dao.PendingTransactionDao
import com.ekspensify.app.room.model.PendingTransactionModel


@Database(
    entities = [PendingTransactionModel::class],
    version = 3,
    exportSchema = false
)
abstract class EkspensifyDatabase : RoomDatabase() {

    abstract fun pendingTransactionDao(): PendingTransactionDao

    companion object {


        @Volatile
        private var INSTANCE: EkspensifyDatabase? = null


        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE pending_transaction ADD COLUMN notification_id TEXT")
            }
        }

        /**
         * THis Function was created for SMS WORK MANAGER WHERE WE CANT Inject Database using HILT
         * no we are using Notification service for sms tracking so this functions is not in use.
         */
        fun getDataBase(context: Context): EkspensifyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = EkspensifyDatabase::class.java,
                    name = "ekspensify_db"
                )
                    .addMigrations(MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}