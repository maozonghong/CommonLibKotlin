package com.mao.library.interfaces

import android.content.Intent

interface OnActivityResultListener {

  fun onActivityResult(activity: ActivityInterface, requestCode: Int, resultCode: Int, data: Intent?)
}
