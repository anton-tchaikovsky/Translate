package com.example.translate.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RoomTranslateDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoomTranslateEntity(roomTranslateEntity: RoomTranslateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>)

    @Query("SELECT*FROM translate_table WHERE id = :id")
    fun readRoomTranslateEntity(id: Int): RoomTranslateEntity?

    @Query("SELECT*FROM translate_table WHERE eng_text = :text")
    fun readRoomTranslateEntity(text: String): RoomTranslateEntity?

    @Query("SELECT*FROM translate_table")
    fun readListRoomTranslateEntity(): List<RoomTranslateEntity>

    @Query("DELETE FROM translate_table WHERE id = :id")
    fun deleteRoomTranslateEntity(id: Int)

    @Delete
    fun deleteRoomTranslateEntity(roomTranslateEntity: RoomTranslateEntity)

    @Query("DELETE FROM translate_table WHERE eng_text = :text")
    fun deleteRoomTranslateEntity(text: String)

    @Delete
    fun deleteListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>)

    @Query("DELETE FROM translate_table")
    fun cleareTranslateTable()

}