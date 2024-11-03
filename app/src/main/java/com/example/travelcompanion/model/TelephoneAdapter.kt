package com.example.travelcompanion.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.annotations.JsonAdapter
import java.lang.reflect.Type

@JsonAdapter(TelephoneAdapter::class)
data class Telephone(val numbers: List<String>) {
    override fun toString(): String = numbers.joinToString(", ")
}

class TelephoneAdapter : JsonDeserializer<Telephone> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Telephone {
        return if (json.isJsonArray) {
            Telephone(json.asJsonArray.map { it.asString })
        } else {
            Telephone(listOf(json.asString))
        }
    }
}
