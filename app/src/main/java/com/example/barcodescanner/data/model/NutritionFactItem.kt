package com.example.barcodescanner.data.model

import org.json.JSONObject

data class NutritionFactItem(
    val name: String,
    val value: String
)

val nutritionKeyMap = mapOf(
    "sodium" to "나트륨",
    "sugars" to "당류",
    "carbohydrates" to "탄수화물",
    "fat" to "지방",
    "trans_fat" to "트랜스지방",
    "saturated_fat" to "포화지방",
    "cholesterol" to "콜레스테롤",
    "protein" to "단백질",
    "calcium" to "칼슘"
)

val nutritionUnitMap = mapOf(
    "sodium" to "mg",
    "sugars" to "g",
    "carbohydrates" to "g",
    "fat" to "g",
    "trans_fat" to "g",
    "saturated_fat" to "g",
    "cholesterol" to "mg",
    "protein" to "g",
    "calcium" to "mg"
)

fun parseNutritionJson(json: String): Pair<List<NutritionFactItem>, List<String>> {
    val facts = mutableListOf<NutritionFactItem>()
    val allergens = mutableListOf<String>()

    try {
        val root = JSONObject(json)

        // --- allergens 파싱 ---
        if (root.has("allergens")) {
            val array = root.getJSONArray("allergens")
            for (i in 0 until array.length()) {
                allergens.add(array.getString(i))
            }
        }

        // --- nutrition 파싱 ---
        if (root.has("nutrition")) {
            val nutritionObj = root.getJSONObject("nutrition")
            val keys = nutritionObj.keys()

            while (keys.hasNext()) {
                val key = keys.next()
                val rawValue = nutritionObj.getString(key)

                if (rawValue.isNotBlank()) {
                    val korean = nutritionKeyMap[key] ?: key
                    val unit = nutritionUnitMap[key] ?: ""

                    val valueWithUnit =
                        if (rawValue.any { it.isDigit() }) "$rawValue $unit"
                        else rawValue

                    facts.add(NutritionFactItem(name = korean, value = valueWithUnit))
                }
            }
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return facts to allergens
}
