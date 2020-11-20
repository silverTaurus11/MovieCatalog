package com.example.moviecatalog.utils

import java.text.SimpleDateFormat
import java.util.*

object MovieCatalogueHelper {

    fun reCalculateRating(rawRating: Float?): Float = ((rawRating?:0f) * 5f) / 10f

    fun getYearFromFullDate(dateString: String?): String{
        if(dateString == null) return ""
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = simpleDateFormat.parse(dateString)
        if(date != null){
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar.get(Calendar.YEAR).toString()
        }
        return ""
    }

    fun getFilterDateString(): String{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -5)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return simpleDateFormat.format(calendar.time)
    }
}