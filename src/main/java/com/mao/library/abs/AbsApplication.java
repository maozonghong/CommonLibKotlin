package com.mao.library.abs;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.mao.library.bean.DownloadInfo;
import com.mao.library.dbHelper.DownloadInfoDbHelper;
import com.mao.library.manager.ThreadPoolManager;
import com.mao.library.utils.ToastUtil;

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

            if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
                try {
                    DEVICEDID= Build.getSerial();
                    Log.e("AbsApplication","getSerail:"+DEVICEDID);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(DEVICEDID.equals(Build.UNKNOWN)){
                //ANDROID_ID 设备被重置之后 会变
                DEVICEDID=android.provider.Settings.Secure.getString(getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }

        }

        if(isOnMainProcess()){
            ActivityCompatManager.init();
            initApplication();
            instance=this;
        }

    }

    public static String getPACKAGENAME() {
        return PACKAGENAME;
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
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
                fileDir=getFilesDir();
            }else{
                fileDir = new File(Environment.getExternalStorageDirectory(), PACKAGENAME);
            }
        } else {
            fileDir = getInstance().getCacheDir();
        }

        if (fileDir == null) {
            fileDir =getFilesDir(); ///data/data/包名/files
        }

        fileDir.mkdirs();
        return fileDir;
    }


    public  int getVersionCode() {
        return VERSION_CODE;
    }

    public  String getVERSION() {
        return VERSION;
    }

    public static boolean hasSdCard(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 清理缓存目录
     */
    public void startClearCacheFolder() {
        ThreadPoolManager.cacheExecute(() -> {
            File cacheDir = getCacheDir();
            if (cacheDir != null) {
                Log.i("mao", "共删除：" + clearCacheFolder(cacheDir));
            }
            ToastUtil.showOkToast("清理缓存完成");
        });
    }

    public static int clearCacheFolder(File dir) {
        int deletedFiles = 0;
        if (dir != null) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File child : files) {
                    if (child != null) {
                        if (child.isDirectory()) {
                            deletedFiles += clearCacheFolder(child);
                        }
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            }
        }
        return deletedFiles;
    }


    /**
     * 获取缓存文件大小
     * @param dir
     * @return
     */
    public long getCacheFileSize(File dir) {
        long filesize = 0;
        if (dir != null && dir.listFiles() != null) {
            for (File child : dir.listFiles()) {
                if (child != null) {
                    if (child.isDirectory()) {
                        filesize += getCacheFileSize(child);
                    }
                    filesize += child.length();
                }
            }
        }
        return filesize;
    }

}
