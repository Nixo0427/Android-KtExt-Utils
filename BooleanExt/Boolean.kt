package github.nixo.com.common


/*这个是密封类，相当于枚举类的扩展类
   在Kotlin1.1之前密封类的子类必须嵌套在密封类中，
   Kotlin1.1之后，没有该限制，这样做的好处是在使用when函数的时候，能表达基本所有选项，尽量可以不用使用else
   out 协变 相当于java的<? extend T> 只可进行返回T类型，属于生产者
 */
sealed class BooleanExt<out T>

/**
 * 扩展类的对象。
 */
object Otherwise:BooleanExt<Nothing>()

class withData<T>(val data:T):BooleanExt<T>()

/**
 * 内连函数，编译时直接插入代码块中
 */

inline fun <T> Boolean.yes(block:() -> T) = when(this){
    this ->{
        withData( block())
    }
    else ->{
        Otherwise
    }
}



inline fun <T>BooleanExt<T>.otherwise(block: () -> T):T = when(this){
    is Otherwise -> block()
    is withData -> this.data
}


inline fun <T>Boolean.no(block: () -> T):BooleanExt<T> =
        when{
            this->{
                Otherwise
            }
            else -> {

                withData(block())
            }
        }