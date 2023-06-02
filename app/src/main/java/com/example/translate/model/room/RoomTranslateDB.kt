package com.example.translate.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomTranslateEntity::class], version = 1)
abstract class RoomTranslateDB: RoomDatabase() {

    abstract fun getRoomDAO():RoomTranslateDAO

}