package com.example.translate.view_model.translate_view_model

import com.example.translate.model.room.RoomTranslateEntity

interface ITranslateViewModel {

    var isOnline: Boolean

    fun onSearchWord(text: String?)

    fun onChangingInputWord(inputWord: String?)

    fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>)

}