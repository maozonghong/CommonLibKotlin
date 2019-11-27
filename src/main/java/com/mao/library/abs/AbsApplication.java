package com.mao.library.abs;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;

import androidx.multidex.MultiDexApplication;

import com.mao.library.bean.DownloadInfo;
import com.mao.library.dbHelper.DownloadInfoDbHelper;

import java.io.File;
import java.util.List;

/**
 * Created by maozonghong
 * on 2019/11/21
 */
public class AbsApplication extends MultiDexApplication {
    protected static AbsApplication instance;
    public static String VERSION,DEVICEDID,PACKAGENAME,PROCESS_NAME,APPNAME;
    public static int VERSION_CODE=1;

    @Override
    public void onCreate() {
        super.onCreate();

        PackageManager packageManager=getPackageManager();
        try {
            PackageInfo info= packageManager.getPackageInfo(getPackageName(),0);
            VERSION=info.versionName;
            VERSION_CODE=info.versionCode;
            PACKAGENAME=info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        PROCESS_NAME=getCurProcessName();
        APPNAME = packageManager.getApplicationLabel(getApplicationInfo()).toString();

        DEVICEDID=android.os.Build.SERIAL;
        if(DEVICEDID.equals(Build.UNKNOWN)){
            //ANDROID_ID 设备被重置之后 会变
            DEVICEDID=android.provider.Settings.Secure.getString(getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
        }

        if(isOnMainProcess()){
            ActivityCompatManager.init();
            initApplication();
            instance=this;
        }
    }

    public static AbsApplication getInstance(){
        return instance;
    }

    public String getDeviceId() {
        return DEVICEDID;
    }

    public static boolean isOnMainProcess() {
        return PROCESS_NAME.equals(PACKAGENAME);
    }

    public String getCurProcessName() {
        int pid = Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> infos = mActivityManager.getRunningAppProcesses();
        if (infos != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : infos) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        }
        return "";
    }

    protected void initApplication() {
        initDB();
    }

    protected void initDB() {
        AbsDbHelper.registerDB(DownloadInfo.class, DownloadInfoDbHelper.class);
    }

    public String getCachePath(){
        File cacheDir=this.getCacheDir();
        if(cacheDir!=null){
            return cacheDir.getPath();
        }else{
            return null;
        }
    }
    public String getDB_NAME() {
        return PACKAGENAME+".db";
    }

    public File getCacheDir() {
        File cacheDir = null;
        if(hasSdCard()) {
            cacheDir = getExternalCacheDir();// /sdcard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
        } else {
            cacheDir = super.getCacheDir();// /data/user/0/应用包名/cache
        }

        if(cacheDir == null) {
            cacheDir = this.getFilesDir();
        }

        return cacheDir;
    }


    /**
     * 获取文件目录，该目录下文件不会因卸载或清空数据被删除
     *
     * @return
     */
    public String getFileDirPath() {
        File fileDir = getFileDir();
        if (fileDir != null) {
            return fileDir.getPath();
        } else {
            return null;
        }
    }

    public File getFileDir() {
        File fileDir;
        if (hasSdCard()) {
            fileDir = new File(Environment.getExternalStorageDirectory(), PACKAGENAME);
        } else {
            fileDir = getInstance().getCacheDir();
        }

        if (fileDir == null) {
            fileDir = getInstance().getFilesDir(); ///data/data/包名/files
        }

        fileDir.mkdirs();
        return fileDir;
    }

    public static boolean hasSdCard(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

}
