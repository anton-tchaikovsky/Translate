package com.example.translate.view_model.translate_favorites_view_model

import com.example.core.interactor.IChangingFavoritesStateInteractor
import com.example.translate.interactor.translate_history_interactor.ITranslateHistoryInteractor
import com.example.translate.view_model.translate_history_view_model.TranslateHistoryViewModel

class TranslateFavoritesViewModel(changingFavoritesStateInteractor: IChangingFavoritesStateInteractor, translateFavoritesInteractor: ITranslateHistoryInteractor) :
    TranslateHistoryViewModel(changingFavoritesStateInteractor, translateFavoritesInteractor) {

    override val emptiData: String = "Список избранных слов пуст"

}