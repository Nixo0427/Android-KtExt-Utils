package github.nixo.com.github.Ext

object SharedSetting{
    var account : String by SharedExt(AppContext,"account","")
    var passWord : String by SharedExt(AppContext,"password","")
}