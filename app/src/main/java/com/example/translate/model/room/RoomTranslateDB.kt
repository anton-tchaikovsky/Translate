package com.example.translate.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [RoomTranslateEntity::class], version = 2)
abstract class RoomTranslateDB: RoomDatabase() {

    abstract fun getRoomDAO():RoomTranslateDAO

    companion object{
        val MIGRATION_1_2 = object: Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE translate_table ADD COLUMN is_favorites TEXT NOT NULL DEFAULT 'false'")
            }
        }
    }

}