package com.mao.library.utils;

import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.mao.library.abs.AbsApplication;
import com.mao.library.manager.ThreadPoolManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtils {
    private static final String TAG=FileUtils.class.getSimpleName();

    public static long getMediaDuration(File file) {
        return getMediaDuration(file.getPath());
    }

    public static long getMediaDuration(String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(path);
            long result = Long.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            Log.e(TAG, "getMediaDuration duration:"+result  +"   path:" + path );
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return 0;
    }


    public static void moveFile(File oldFile, File newFile) {
        if (oldFile.exists()) {
            if (oldFile.isDirectory()) {
                newFile.mkdirs();
                File[] files = oldFile.listFiles();
                Log.i("mao", "moveDirectory---->" + oldFile.getPath() + ":" + files.length + "------->" + newFile.getPath());
                for (File file : files) {
                    moveFile(file, new File(newFile, file.getName()));
                }
            } else {
                newFile.getParentFile().mkdirs();
                Log.i("mao", "moveFile---->" + oldFile.getPath() + "------->" + newFile.getPath());
                if (!oldFile.renameTo(newFile)) {
                    copyFile(oldFile, newFile);
                }
            }

            if (!oldFile.delete()) {
                oldFile.deleteOnExit();
            }
        }
    }

    public static boolean copyFile(String oldFile, String newFile) {
        return copyFile(new File(oldFile), new File(newFile));
    }

    public static boolean copyFile(File oldFile, File newFile) {
        if (oldFile.exists()) {
            newFile.getParentFile().mkdirs();

            InputStream fis = null;
            FileOutputStream fos = null;
            try {
                int byteread = 0;
                fis = new FileInputStream(oldFile);
                fos = new FileOutputStream(newFile);
                byte[] buffer = new byte[1024];
                while ((byteread = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteread);
                }
                fis.close();
                fos.close();
            } catch (Exception e) {
                Log.i("mao", "复制错误:  oldFile=" + oldFile.getPath() + "   newFile=" + newFile.getPath());
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

    public static void cleanDirectory(File dir) {
        if (dir == null) {
            return;
        }

        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    deleteFile(file);
                }
            }
        }
    }

    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }

        if (file.exists()) {
            if (file.isDirectory()) {
                for (File child : file.listFiles()) {
                    deleteFile(child);
                }
            }

            if (!file.delete()) {
                file.deleteOnExit();
            }
        }
    }

    public static void deleteCache(String url) {
        FileUtils.deleteFile(new File(FileUtils.urlToFilename(url)));
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        ExifInterface exif;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }

        if (exif != null) {
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }

        Log.i("mao", "readPictureDegree:" + degree);

        return degree;
    }

    public static String urlToFilename(String url) {
        return urlToFilename(url, true, false);
    }

    public static String urlToFilename(String url, boolean isCache) {
        return urlToFilename(url, isCache, false);
    }

    public static String getFilename(String url) {
        String name = url;
        if (name.contains("?")) {
            int lastIndex = name.lastIndexOf("?");
            String temp = name.substring(0, lastIndex);
            url = temp + name.substring(lastIndex + 1);
            name = temp;
        }

        if (name.contains("/")) {
            name = name.substring(name.lastIndexOf("/") + 1);
        }
        return name;
    }

    public static String urlToFilename(String url, boolean isCache, boolean keepFilename) {
        String name = getFilename(url);

        String type = null;

        if (name.contains(".")) {
            type = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        }

        StringBuilder builder = new StringBuilder(64);
        builder.append(isCache ? AbsApplication.getInstance().getCachePath() : AbsApplication.getInstance().getFileDirPath());
        builder.append("/");

        if (type != null) {
            builder.append(type);
            builder.append("/");
        }

        if (keepFilename) {
            builder.append(name);
        } else {
            builder.append(DigestUtils.getMD5Str(url));
            builder.append(".");
            builder.append(type);
        }

        // Log.i("mao", url+":"+ builder.toString());

        return builder.toString();
    }

    public static String getTempUrl(String url) {
        String name = url;
        if (name.contains("?")) {
            int lastIndex = name.lastIndexOf("?");
            String temp = name.substring(0, lastIndex);
            url = temp + name.substring(lastIndex + 1);
            name = temp;
        }

        if (name.contains("/")) {
            name = name.substring(name.lastIndexOf("/") + 1);
        }

        String type = null;

        if (name.contains(".")) {
            type = name.substring(name.lastIndexOf(".") + 1);
            url = url.substring(0, url.lastIndexOf("."));
        }

        StringBuilder builder = new StringBuilder(64);
        builder.append(url);
        builder.append("_temp");

        if (type != null) {
            builder.append(".");
            builder.append(type);
        }

        return builder.toString();
    }

    public static String getUpLoadFileName(File file) {
        String fileName = file.getName();
        return System.currentTimeMillis() + String.valueOf(file.hashCode()) + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String getSuffix(File file) {
        String path = file.getPath();
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static String getSimpleName(File file) {
        String name = file.getName();
        return name.substring(0, name.lastIndexOf("."));
    }

    public static void renameTo(File file, File newFile) {
        if (!file.renameTo(newFile)) {
            FileUtils.copyFile(file, newFile);
            if (!file.delete()) {
                file.deleteOnExit();
            }
        }
    }


    public static String getFromAssets(String fileName) {
        String result = "";
        try {
            InputStream in = AbsApplication.getInstance().getResources().getAssets().open(fileName);
            int lenght = in.available();
            byte[] buffer = new byte[lenght];
            in.read(buffer);
            result = new String(buffer, Charset.defaultCharset());// 你的文件的编码
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String readFileFromRaw(int resourceId) {
        if(resourceId < 0 ){
            return null;
        }

        String result = null;
        try {
            InputStream inputStream = AbsApplication.getInstance().getResources().openRawResource( resourceId );
            // 获取文件的字节数
            int length = inputStream.available();
            // 创建byte数组
            byte[] buffer = new byte[length];
            // 将文件中的数据读到byte数组中
            inputStream.read(buffer);
            result =new String(buffer,Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void unzip(final File file, final String outputDirectory) {
        ThreadPoolManager.cacheExecute(new Runnable() {

            @Override
            public void run() {
                unzipInThread(file, outputDirectory);
            }
        });
    }

    public static void unzipInThread(File file, String outputDirectory) {
        ZipFile zf = null;
        InputStream in = null;
        FileOutputStream out = null;
        try {
            // fis = new FileInputStream(file);
            // in = new ZipInputStream(fis);

            zf = new ZipFile(file);

            String name = "";
            // String extractedFile = "";
            // int counter = 0;
            File dir = new File(outputDirectory);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Enumeration<? extends ZipEntry> entries = zf.entries();

            while (entries.hasMoreElements()) {
                ZipEntry z = entries.nextElement();

                name = z.getName();
                if (z.isDirectory()) {
                    // get the folder name of the widget
                    name = name.substring(0, name.length() - 1);
                    File folder = new File(outputDirectory + File.separator + name);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }
                    // if (counter == 0) {
                    // extractedFile = folder.toString();
                    // }
                    // counter++;
                } else {
                    File f = new File(outputDirectory + File.separator + name);
                    if (!f.getParentFile().exists()) {
                        // 判断文件夹是否存在
                        f.getParentFile().mkdir(); // 创建文件夹
                    }

                    in = zf.getInputStream(z);

                    // Log.i("nero", "unzipFile: " + f.getPath());
                    // if (!file.exists() && file.length() == 0) {
                    // get the output stream of the file
                    out = new FileOutputStream(f);
                    int ch;
                    byte[] buffer = new byte[1024];
                    // read (ch) bytes into buffer
                    while ((ch = in.read(buffer)) != -1) {
                        // write (ch) byte from buffer at the
                        // position 0
                        out.write(buffer, 0, ch);
                    }
                    out.flush();
                    out.close();
                    out = null;

                    in.close();
                    in = null;
                    // }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }

            if (zf != null) {
                try {
                    zf.close();
                } catch (Exception e) {
                }
            }
        }
    }


    public static final boolean saveFile(String s, File file) {
        return saveFile(s, file.getPath());
    }

    public static final boolean saveFile(String s, String path) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(s.getBytes());
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public static final String loadFile(File file) {
        return loadFile(file.getPath());
    }

    public static final String loadFile(String path) {
        FileInputStream fis = null;
        ByteArrayOutputStream outStream = null;
        try {
            fis = new FileInputStream(path);

            outStream = new ByteArrayOutputStream();
            byte[] data = new byte[512];
            int count=-1;
            while ((count = fis.read(data, 0, data.length)) != -1) {
                outStream.write(data, 0, count);
            }

            return new String(outStream.toByteArray(), "UTF-8");
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static String getDataSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    /** 获取SD卡剩余容量 （单位MB） */
    public static long getSDFreeSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大小
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /** 获取SD卡总容量 （单位MB） */
    public static long getSDAllSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 获取所有数据块数
        long allBlocks = sf.getBlockCount();
        // 返回SD卡大小
        return (allBlocks * blockSize) / 1024 / 1024; // 单位MB
    }


}
