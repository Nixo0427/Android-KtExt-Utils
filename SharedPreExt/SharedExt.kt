package github.nixo.com.github.Ext

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SharedExt<T>(val context : Context , val key : String , val value : T , val defName :
String = "SharedPreferences")
    :ReadWriteProperty<Any?,T> {

    //读写代理我们的SharedExt，第一个泛型参数是代理的类型，这里必须是实体不能使泛型，第二个为参数泛型，可以是实体可以是泛型


    //懒加载  -》 适用于初始化val类型常量，一次初始化，不改变，多次使用。
    //同时lazy也是一种属性委托，将SharedPreferences的初始化所生成的属性委托给sp
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