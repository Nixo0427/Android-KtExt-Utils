package github.nixo.com.github.Ext

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedExt<T>(val context : Context , val key : String , val value : T , val defName :
String = "SharedPreferences")
    :ReadWriteProperty<Any?,T> {

    //属性代理我们的SharedPreferences进行初始化，以后输入sp就相当于代理的初始化了。
    private val sp by lazy{
        context.getSharedPreferences(defName,Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T  = getSP(key)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {

        setSP(key,value)

    }
    private fun getSP(key: String): T = when(value){
        is String -> sp.getString(key,"")
        is Int -> sp.getInt(key,0)
        is Long -> sp.getLong(key,0)
        is Float -> sp.getFloat(key,0f)
        is Boolean -> sp.getBoolean(key,false)
        else -> throw IllegalAccessException("UnKnow Type.")

    }as T

    private fun setSP(key: String ,value: T){
        with(sp.edit()){
            when(value){
                is String -> putString(key,value)
                is Int -> putInt(key,value)
                is Long -> putLong(key,value)
                is Float -> putFloat(key,value)
                is Boolean -> putBoolean(key,value)
                else ->throw IllegalAccessException("UnKnow Type.")

            }
        }
    }

}