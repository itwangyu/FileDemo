package com.xtoolapp.file.filedemo.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件相关辅助类
 *
 * @author panyi
 */
public class FileUtils {
    public static final String FOLDER_NAME = "cache";

    public static void writeLog(String strLog) {

        try {
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"AFileDemo";
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(filePath+File.separator+"ALog.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file,true);

            do {
                fos.write((strLog + "---***---\n").getBytes());
                fos.flush();
            } while (false);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * @param folderName 文件夹名称
     * @return
     */
    public static File createFolder(String folderName) {
        File baseDir;

        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            baseDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "AFileDemo");
        } else {
            baseDir = new File(Environment.getDataDirectory(), "AFileDemo");
        }

        File aviaryFolder = new File(baseDir, folderName);
        if (aviaryFolder.exists())
            return aviaryFolder;
        if (aviaryFolder.isFile())
            aviaryFolder.delete();
        if (aviaryFolder.mkdirs())
            return aviaryFolder;

        return Environment.getDataDirectory();
    }

    /**
     * 获取存贮文件的文件夹路径
     *
     * @return
     */
    public static File createCacheFolders() {
        File baseDir;

        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            baseDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "AFileDemo");
        } else {
            baseDir = new File(Environment.getDataDirectory(), "AFileDemo");
        }


        File aviaryFolder = new File(baseDir, FOLDER_NAME);
        if (aviaryFolder.exists())
            return aviaryFolder;
        if (aviaryFolder.isFile())
            aviaryFolder.delete();
        if (aviaryFolder.mkdirs())
            return aviaryFolder;

        return Environment.getExternalStorageDirectory();
    }

    public static File genEditFile() {
        return FileUtils.getEmptyFile("tietu"
                + System.currentTimeMillis() + ".png");
    }

    public static File getEmptyFile(String name) {
        File folder = FileUtils.createCacheFolders();
        if (folder != null) {
            if (folder.exists()) {
                File file = new File(folder, name);
                return file;
            }
        }
        return null;
    }


    /**
     * 删除文件或目录下所有文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else {

                File[] files = file.listFiles();
                if (files.length == 0) {
                    file.delete();
                }
                for (File f : files) {
                    deleteFile(f);
                }

            }
        }

    }

    /**
     * 删除指定文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFileNoThrow(String path) {
        File file;
        try {
            file = new File(path);
        } catch (NullPointerException e) {
            return false;
        }

        if (file.exists()) {
            return file.delete();
        }
        return false;
    }


    public static String saveBitmap(String dirFile, String bitName, Bitmap mBitmap) {
        if (TextUtils.isEmpty(dirFile) || TextUtils.isEmpty(bitName) || mBitmap == null || mBitmap.isRecycled()) {
            return null;
        }
        File f = new File(dirFile, bitName);
        FileOutputStream fOut = null;
        try {

            if (f.createNewFile()) {
                fOut = new FileOutputStream(f);
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fOut != null) {
                try {
                    fOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return f.getAbsolutePath();
    }


    /**
     * 保存图片
     *
     * @param bitName
     * @param mBitmap
     */
    public static String saveBitmap(String bitName, Bitmap mBitmap) {
        if (TextUtils.isEmpty(bitName) || mBitmap == null || mBitmap.isRecycled()) {
            return null;
        }
        File baseFolder = createCacheFolders();
        File f = new File(baseFolder.getAbsolutePath(), bitName);
        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f.getAbsolutePath();
    }

    // 获取文件夹大小
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) { // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位 * * @param size * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024d;
        int megaByte = (int) (kiloByte / 1024d);
        return megaByte + "MB";
    }

    /**
     * 复制文件
     *
     * @param fromFile
     * @param toFile
     */
    public static void copyFile(File fromFile, File toFile) {
        try {
            FileInputStream ins = new FileInputStream(fromFile);
            FileOutputStream out = new FileOutputStream(toFile);
            byte[] b = new byte[1024];
            int n = 0;
            while ((n = ins.read(b)) != -1) {
                out.write(b, 0, n);
            }

            ins.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
