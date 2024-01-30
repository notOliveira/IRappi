package com.example.irappi.models

import Measures

// Caso algo dê errado, lembrar que os ids eram inicializados com 0
class ExtendedIngredient(val id: Int, var aisle: String? = null, var image: String? = null, var consistency: String? = null, var name: String? = null, var nameClean: String? = null, var original: String? = null, var originalName: String? = null, var amount: Double = 0.0, var unit: String? = null, var meta: ArrayList<String>? = null, var measures: Measures)

