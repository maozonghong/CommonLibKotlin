package com.mao.library.dbHelper

import com.mao.library.abs.AbsDbHelper
import com.mao.library.bean.DownloadInfo

/**
 * Created by maozonghong
 * on 2019/11/21
 */
class DownloadInfoDbHelper : AbsDbHelper<DownloadInfo> {

    constructor()

    constructor(byUser: String) : super(byUser)

    override fun getTabName(): String {
        return "table_download_info"
    }
}
