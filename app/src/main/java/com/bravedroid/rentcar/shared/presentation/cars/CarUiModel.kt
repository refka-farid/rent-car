package com.bravedroid.rentcar.shared.presentation.cars

import java.util.*

data class CarUiModel(
    val id: String,
    val name: String,
) {
//    override fun equals(other: Any?): Boolean {
//        return other is CarUiModel
//                && this.id == other.id
//                && this.name == other.name
//    }
//
//    override fun hashCode(): Int {
//        return Objects.hash(id,name)
//    }
}
