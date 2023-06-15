package com.example.model

import com.example.model.data.TranslateEntity
import com.example.model.data.dto.DataModel
import com.example.model.data.dto.DataModelItem
import com.example.model.data.dto.Meaning
import com.example.model.data.dto.Translation
import com.example.model.data.mapper.DataMapper
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNotSame
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertSame
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class DataMapperTest {

    @Test
    fun extractImageUrl_CorrectUrl_ReturnsUrl() {
        assertEquals(
            DataMapper.extractImageUrl("//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg"),
            "https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg"
        )
        assertEquals(
            DataMapper.extractImageUrl("//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/HTTPS://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg"),
            "https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg"
        )
        assertNotEquals(
            DataMapper.extractImageUrl("//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/HTTPS://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg"),
            "HTTPS://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg"
        )
    }

    @Test
    fun extractImageUrl_InvalidUrl_ReturnsEmpty() {
        assertEquals(
            DataMapper.extractImageUrl("//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg"),
            ""
        )
    }

    @Test
    fun extractImageUrl_NullOrBlankUrl_ReturnsEmpty() {
        assertEquals(
            DataMapper.extractImageUrl(null),
            ""
        )
        assertEquals(
            DataMapper.extractImageUrl(""),
            ""
        )
        assertEquals(
            DataMapper.extractImageUrl(" "),
            ""
        )
    }

    @Test
    fun mapFromTranslateToTranslateText_CorrectTranslation_ReturnsTranslateText() {
        assertEquals(
            DataMapper.mapFromTranslateToTranslateText(Translation("кот", "кошка")),
            "кот (кошка)"
        )
        assertEquals(DataMapper.mapFromTranslateToTranslateText(Translation("кот", "")), "кот")
        assertEquals(DataMapper.mapFromTranslateToTranslateText(Translation("кот", " ")), "кот")
        assertEquals(DataMapper.mapFromTranslateToTranslateText(Translation("кот", null)), "кот")
        assertEquals(
            DataMapper.mapFromTranslateToTranslateText(Translation("", "кошка")),
            "кошка"
        )
        assertEquals(
            DataMapper.mapFromTranslateToTranslateText(Translation(" ", "кошка")),
            "кошка"
        )
        assertEquals(
            DataMapper.mapFromTranslateToTranslateText(Translation(null, "кошка")),
            "кошка"
        )
    }

    @Test
    fun mapFromTranslateToTranslateText_InvalidTranslation_ReturnsEmpty() {
        assertEquals(
            DataMapper.mapFromTranslateToTranslateText(Translation("", "")),
            ""
        )
        assertEquals(
            DataMapper.mapFromTranslateToTranslateText(Translation(" ", " ")),
            ""
        )
        assertEquals(
            DataMapper.mapFromTranslateToTranslateText(Translation("", " ")),
            ""
        )
        assertEquals(
            DataMapper.mapFromTranslateToTranslateText(Translation(" ", "")),
            ""
        )
        assertEquals(DataMapper.mapFromTranslateToTranslateText(Translation(null, "")), "")
        assertEquals(DataMapper.mapFromTranslateToTranslateText(Translation(null, " ")), "")
        assertEquals(DataMapper.mapFromTranslateToTranslateText(Translation("", null)), "")
        assertEquals(DataMapper.mapFromTranslateToTranslateText(Translation(" ", null)), "")
        assertEquals(DataMapper.mapFromTranslateToTranslateText(Translation(null, null)), "")
    }

    @Test
    fun mapFromDataModelItemToTranslateEntity_CorrectDataModelItem_ReturnsTranslateEntity() {
        assertEquals(
            DataMapper.mapFromDataModelItemToTranslateEntity(
                DataModelItem(
                    1560,
                    "cat",
                    listOf(
                        Meaning(
                            "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                            "kæt",
                            Translation("кот", "кошка")
                        )
                    )
                )
            ), TranslateEntity(
                1560,
                "cat",
                "kæt",
                "кот (кошка)",
                "https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                false
            )
        )
        assertEquals(
            DataMapper.mapFromDataModelItemToTranslateEntity(
                DataModelItem(
                    1560,
                    "cat",
                    listOf(
                        Meaning(
                            "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                            null,
                            Translation("кот", "кошка")
                        )
                    )
                )
            ), TranslateEntity(
                1560,
                "cat",
                "",
                "кот (кошка)",
                "https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                false
            )
        )
    }

    @Test
    fun mapFromDataModelItemToTranslateEntity_InvalidDataModelItem_ReturnsNull() {
        assertNull(
            DataMapper.mapFromDataModelItemToTranslateEntity(
                DataModelItem(
                    null,
                    "cat",
                    listOf(
                        Meaning(
                            "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                            "kæt",
                            Translation("кот", "кошка")
                        )
                    )
                )
            )
        )
        assertNull(
            DataMapper.mapFromDataModelItemToTranslateEntity(
                DataModelItem(
                    1560,
                    " ",
                    listOf(
                        Meaning(
                            "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                            "kæt",
                            Translation("кот", "кошка")
                        )
                    )
                )
            )
        )
        assertNull(
            DataMapper.mapFromDataModelItemToTranslateEntity(
                DataModelItem(
                    1560,
                    null,
                    listOf(
                        Meaning(
                            "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                            "kæt",
                            Translation("кот", "кошка")
                        )
                    )
                )
            )
        )
        assertNull(
            DataMapper.mapFromDataModelItemToTranslateEntity(
                DataModelItem(
                    1560,
                    "cat",
                    listOf(
                    )
                )
            )
        )

        assertNull(
            DataMapper.mapFromDataModelItemToTranslateEntity(
                DataModelItem(
                    1560,
                    "cat",
                    null
                )
            )
        )
        assertNull(
            DataMapper.mapFromDataModelItemToTranslateEntity(
                DataModelItem(
                    1560,
                    "cat",
                    listOf(
                        Meaning(
                            "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                            "kæt",
                            null
                        )
                    )
                )
            )
        )
        assertNull(
            DataMapper.mapFromDataModelItemToTranslateEntity(
                DataModelItem(
                    1560,
                    "cat",
                    listOf(
                        Meaning(
                            "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                            "kæt",
                            Translation("", "")
                        )
                    )
                )
            )
        )
        assertNull(
            DataMapper.mapFromDataModelItemToTranslateEntity(
                DataModelItem(
                    1560,
                    "cat",
                    listOf(
                        Meaning(
                            "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                            "kæt",
                            Translation(null, null)
                        )
                    )
                )
            )
        )
        assertNotNull(
            DataMapper.mapFromDataModelItemToTranslateEntity(
                DataModelItem(
                    1560,
                    "cat",
                    listOf(
                        Meaning(
                            "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                            "kæt",
                            Translation("кот", null)
                        )
                    )
                )
            )
        )
        assertNotNull(
            DataMapper.mapFromDataModelItemToTranslateEntity(
                DataModelItem(
                    1560,
                    "cat",
                    listOf(
                        Meaning(
                            "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                            "kæt",
                            Translation(null, "кошка")
                        )
                    )
                )
            )
        )
    }

    @Test
    fun mapFromDataModeToListTranslateEntity_CorrectDataModel_ReturnsListTranslatyEntity() {
        val dataModel = DataModel().apply {
            add(
                DataModelItem(
                    1560,
                    "cat",
                    listOf(
                        Meaning(
                            "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                            "kæt",
                            Translation("кот", "кошка")
                        )
                    )
                )
            )
            add(
                DataModelItem(
                    174834,
                    "CAT",
                    listOf(
                        Meaning(
                            "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/7f116a91c1912c5cc24b2f44d0b945f3.png",
                            "kæt",
                            Translation("программно-техническое средство ", null)
                        )
                    )
                )
            )
        }
        val listTranslateEntity = listOf(
            TranslateEntity(
                1560,
                "cat",
                "kæt",
                "кот (кошка)",
                "https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                false
            ),
            TranslateEntity(
                174834,
                "CAT",
                "kæt",
                "программно-техническое средство ",
                "https://cdn-user77752.skyeng.ru/images/7f116a91c1912c5cc24b2f44d0b945f3.png",
                false
            ),
        )
        assertArrayEquals(
            DataMapper.mapFromDataModeToListTranslateEntity(dataModel).toTypedArray(),
            listTranslateEntity.toTypedArray()
        )
        dataModel.add(1, null)
        assertArrayEquals(
            DataMapper.mapFromDataModeToListTranslateEntity(dataModel).toTypedArray(),
            listTranslateEntity.toTypedArray()
        )
        dataModel.add(
            DataModelItem(
                null,
                "cat",
                listOf(
                    Meaning(
                        "//cdn-user77752.skyeng.ru/skyconvert/unsafe/640x480/https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
                        "kæt",
                        Translation("кот", "кошка")
                    )
                )
            )
        )
        assertArrayEquals(
            DataMapper.mapFromDataModeToListTranslateEntity(dataModel).toTypedArray(),
            listTranslateEntity.toTypedArray()
        )

    }

    @Test
    fun mapFromDataModeToListTranslateEntity_EmptyOrOnlyNullItemDataModel_ReturnsEmptyListTranslatyEntity() {
        val dataModel = DataModel()
        assertArrayEquals(
            DataMapper.mapFromDataModeToListTranslateEntity(dataModel).toTypedArray(),
            listOf<TranslateEntity>().toTypedArray()
        )
        dataModel.add(null)
        assertArrayEquals(
            DataMapper.mapFromDataModeToListTranslateEntity(dataModel).toTypedArray(),
            listOf<TranslateEntity>().toTypedArray()
        )
    }

    @Suppress("UnnecessaryVariable")
    @Test
    fun exampleAssertSameAndAssertNotSame(){
        val translateEntity = TranslateEntity(
            1560,
            "cat",
            "kæt",
            "кот (кошка)",
            "https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
            false
        )
        val cloneTranslateEntity = translateEntity
        val otherTranslateEntity = TranslateEntity(
            1560,
            "cat",
            "kæt",
            "кот (кошка)",
            "https://cdn-user77752.skyeng.ru/images/55bd5010ef32706be7b7e371673c1b1c.jpeg",
            false
        )
        assertSame(translateEntity, cloneTranslateEntity)
        assertNotSame(translateEntity, otherTranslateEntity)
    }

}