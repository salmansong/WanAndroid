package com.charliesong.wanandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.util.Log;

import com.charliesong.wanandroid.base.BaseActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ActivityTestzip extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       File file= getExternalCacheDir();
        System.out.println("getExternalCacheDir============"+file.getAbsolutePath());
        File file2= getExternalFilesDir(null);
        System.out.println("getExternalFilesDir=============="+file2.getAbsolutePath());
        try {
            String[] files = getAssets().list("");
            System.out.println("=========="+Arrays.toString(files));
//           open("res.zip");
//           open("test/test1.txt");
//            Unzip("files.zip",getExternalFilesDir(null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
private void test(){
    ConstraintWidget constraintWidget=new ConstraintWidget();
    constraintWidget.addToSolver(new LinearSystem());
}
    private void open(String name){
        try {
            InputStream is = getAssets().open(name);
            File out=new File(getExternalFilesDir(""),name);
            if(!out.exists()){
                out.getParentFile().mkdirs();
            }
            FileOutputStream fos=new FileOutputStream(out);
            byte[] bb=new byte[1024];
            int index=-1;
            while ((index=is.read(bb))!=-1){
                fos.write(bb,0,index);
            }
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private  void Unzip(String zipFile, File targetDir) {
        int BUFFER = 4096; //这里缓冲区我们使用4KB，
        String strEntry; //保存每个zip的条目名称
        try {
            BufferedOutputStream dest = null; //缓冲输出流
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(getAssets().open(zipFile)));
            ZipEntry entry; //每个zip条目的实例

            while ((entry = zis.getNextEntry()) != null) {
                try {
                    Log.i("Unzip: ","="+ entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();

                    File entryFile = new File(targetDir ,strEntry);
                    if(entry.isDirectory()){
                        entryFile.mkdirs();
                        continue;
                    }
                    FileOutputStream fos = new FileOutputStream(entryFile);
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zis.close();
        } catch (Exception cwj) {
            cwj.printStackTrace();
        }
    }


    private  void UnzipToDest(String zipFile, File targetDir) {
        int BUFFER = 4096; //这里缓冲区我们使用4KB，
        String strEntry; //保存每个zip的条目名称

        try {
            BufferedOutputStream dest = null; //缓冲输出流
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry; //每个zip条目的实例

            while ((entry = zis.getNextEntry()) != null) {

                try {
                    Log.i("Unzip: ","="+ entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();

                    File entryFile = new File(targetDir ,strEntry);
                    if(entry.isDirectory()){
                        entryFile.mkdirs();
                        continue;
                    }else{
                        File entryDir = entryFile.getParentFile();
                        if (!entryDir.exists()) {
                            entryDir.mkdirs();
                        }
                    }

                    FileOutputStream fos = new FileOutputStream(entryFile);
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zis.close();
        } catch (Exception cwj) {
            cwj.printStackTrace();
        }
    }

}
