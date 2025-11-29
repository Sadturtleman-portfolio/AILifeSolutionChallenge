package com.example.barcodescanner.data.model

import com.example.barcodescanner.data.response.NutritionItem

data class IngredientModel(
    val foodName: String? = null,        // 식품명
    val makerName: String? = null,       // 제조사
    val servingSize: String? = null,     // 1회 제공량

    val energy: String? = null,          // AMT_NUM1
    val carbohydrates: String? = null,   // AMT_NUM2
    val protein: String? = null,         // AMT_NUM3
    val fat: String? = null,             // AMT_NUM4
    val sodium: String? = null,          // AMT_NUM6

    val saturatedFat: String? = null,    // AMT_NUM9
    val transFat: String? = null,        // AMT_NUM10
    val cholesterol: String? = null      // AMT_NUM11
)

fun NutritionItem.toIngredientModel(): IngredientModel {
    return IngredientModel(
        foodName = this.FOOD_NM_KR,
        makerName = this.MAKER_NM,
        servingSize = this.SERVING_SIZE,

        energy = this.AMT_NUM1,
        carbohydrates = this.AMT_NUM2,
        protein = this.AMT_NUM3,
        fat = this.AMT_NUM4,
        sodium = this.AMT_NUM6,

        saturatedFat = this.AMT_NUM9,
        transFat = this.AMT_NUM10,
        cholesterol = this.AMT_NUM11
    )
}