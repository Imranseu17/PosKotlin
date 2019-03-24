package com.example.pos_kotlin

import android.content.Context

class SharedDataSaveLoad {

    companion object  {
        @JvmStatic

    fun save(context: Context, key: String, value: String) {
        val sharedPreferences = context.getSharedPreferences(
            context.resources.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }
        @JvmStatic
    fun save(context: Context, key: String, value: Boolean) {
        val sharedPreferences = context.getSharedPreferences(
            context.resources.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }
        @JvmStatic
    fun save(context: Context, key: String, value: Int) {
        val sharedPreferences = context.getSharedPreferences(
            context.resources.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.commit()
    }
        @JvmStatic
    fun load(context: Context, key: String): String? {
        val sharedPreferences = context.getSharedPreferences(
            context.resources.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(key, "")
    }
        @JvmStatic
    fun isAvailable(context: Context, key: String): Boolean {
        val sharedPreferences = context.getSharedPreferences(
            context.resources.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(key, false)
    }
        @JvmStatic
    fun saveInt(context: Context, key: String, value: Int) {
        val sharedPreferences = context.getSharedPreferences(
            context.resources.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.commit()
    }
        @JvmStatic
    fun loadInt(context: Context, key: String): Int {
        val sharedPreferences = context.getSharedPreferences(
            context.resources.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getInt(key, 0)
    }
        @JvmStatic
    fun loadInteger(context: Context, key: String): Int {
        val sharedPreferences = context.getSharedPreferences(
            context.resources.getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getInt(key, 0)
    }
        @JvmStatic
        fun loadBoolean(context: Context, key: String): Boolean {
            val sharedPreferences = context.getSharedPreferences(
                context.resources.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
            )
            return sharedPreferences.getBoolean(key, false)
        }



      @JvmStatic
      fun remove(context: Context, key: String) {
          val sharedPreferences = context.getSharedPreferences(
              context.resources.getString(R.string.preference_file_key),
              Context.MODE_PRIVATE
          )
          val editor = sharedPreferences.edit()
          editor.remove(key)
          editor.commit()
      }
  }



}