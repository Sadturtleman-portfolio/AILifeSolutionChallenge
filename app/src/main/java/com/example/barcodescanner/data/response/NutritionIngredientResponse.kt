package com.example.barcodescanner.data.response

import kotlinx.serialization.Serializable

@Serializable
data class FoodNutritionIngredientResponse(
    val header: Header?,
    val body: Body?
)
@Serializable
data class Header(
    val resultCode: String?,
    val resultMsg: String?
)
@Serializable
data class Body(
    val items: Items?,
    val numOfRows: String?,
    val pageNo: String?,
    val totalCount: String?
)
@Serializable
data class Items(
    val item: List<NutritionItem>?
)
@Serializable
data class NutritionItem(
    val FOOD_NM_KR: String? = null,
    val MAKER_NM: String? = null,
    val SERVING_SIZE: String? = null,

    val AMT_NUM1: String? = null,   // 에너지
    val AMT_NUM2: String? = null,   // 탄수화물
    val AMT_NUM3: String? = null,   // 단백질
    val AMT_NUM4: String? = null,   // 지방
    val AMT_NUM6: String? = null,   // 나트륨
    val AMT_NUM9: String? = null,
    val AMT_NUM10: String? = null,
    val AMT_NUM11: String? = null,

    // 나머지 필드는 필요할 때 추가
)
