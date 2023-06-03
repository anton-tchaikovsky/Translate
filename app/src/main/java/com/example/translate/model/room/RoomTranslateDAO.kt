package com.example.translate.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RoomTranslateDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRoomTranslateEntity(roomTranslateEntity: RoomTranslateEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>)

    @Query("SELECT*FROM translate_table WHERE id = :id")
    fun readRoomTranslateEntity(id: Int): RoomTranslateEntity?

    @Query("SELECT*FROM translate_table WHERE eng_text = :text")
    fun readRoomTranslateEntity(text: String): RoomTranslateEntity?

    @Query("SELECT*FROM translate_table WHERE lower (eng_text) BETWEEN :text AND :text || 'zz'  ORDER BY lower(eng_text)")
    fun readListRoomTranslateEntity(text: String): List<RoomTranslateEntity>

    @Query("SELECT*FROM translate_table ORDER BY lower(eng_text)")
    fun readListRoomTranslateEntity(): List<RoomTranslateEntity>

    @Query("SELECT*FROM translate_table WHERE id IN(:listId) ORDER BY lower(eng_text)")
    fun readListRoomTranslateEntityById(listId: List<Int>): List<RoomTranslateEntity>

    @Query("SELECT*FROM translate_table WHERE is_favorites = 'true' ORDER BY lower(eng_text)")
    fun readListFavoritesRoomTranslateEntity(): List<RoomTranslateEntity>

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

    @Update
    fun updateRoomTranslateEntity(roomTranslateEntity: RoomTranslateEntity)

}