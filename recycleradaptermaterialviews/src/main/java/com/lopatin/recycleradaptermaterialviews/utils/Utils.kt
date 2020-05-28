package com.lopatin.recycleradaptermaterialviews.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import com.google.gson.Gson
import com.lopatin.recycleradaptermaterialviews.model.ArrayListContainer
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter

object Utils {

    fun dpToPx(context: Context, dp: Float): Float {
        context.resources?.displayMetrics?.let {
            return dp * (it.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
        return 0f
    }

    fun getJsonStringFromRawJson(context: Context, fileId: Int): String {
        val inputStream = context.resources.openRawResource(fileId)
        val writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n: Int = reader.read(buffer)
            while (n != -1) {
                writer.write(buffer, 0, n)
                n = reader.read(buffer)
            }
        } finally {
            inputStream.close()
        }
        return writer.toString()
    }

    /**
     * метод не работает, потому что тип Т после отработки метода перестает кастоваться в Employee
     * из-за чего возникает ошибка при последующем использовании ArrayList
     */
    fun <T> getTypedListFromJson(jsonString: String): ArrayList<T>? {

        val al = arrayListOf<T>()

        val localArrayContainer = ArrayListContainer<T>(al)
        val qwe = "qwe"
        Log.d("logClass", "${qwe.javaClass}")
        Log.d("logClass", "${localArrayContainer.javaClass}")
        val listFromJson: ArrayListContainer<out T> =
            Gson().fromJson(jsonString, localArrayContainer.javaClass)
        val al2 = arrayListOf<T>()
        for (item in listFromJson.localArrayList) {
            al2.add(item)
            Log.d("logClass", item.toString())
        }
        for (item in al2) {
            Log.d("logClass", "al2 ${item.toString()}")
        }
        return al2
    }


}