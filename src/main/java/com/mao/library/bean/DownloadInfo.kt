package com.mao.library.bean

import com.mao.library.abs.AbsDBModel

class DownloadInfo :AbsDBModel {

    @JvmField
    var uid:String?=null

    @JvmField
    var percent: Int = 0
    @JvmField
    var read:Int= 0
    @JvmField
    var end:Int = 0
    @JvmField
    var newPercent: Float = 0.toFloat()
    @JvmField
    var start: Long = 0

    constructor()

    constructor(uid:String){
        this.uid=uid
    }

    override fun getId(): String? {
        return uid
    }

    override fun setId(str: String?) {
        this.uid=str
    }
}