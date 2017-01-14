package com.bmob.demo.sms;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bmob.demo.sms.bean.ImageFace;


import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobACL;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import okhttp3.internal.Util;

/**
 * Created by liuteng on 2016/12/1.
 */

public class UploadActivity extends BaseActivity {
    private static final String BMOB_APP_KEY="32d9c1b44f58da1592a1bd3465bc2de9";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //final List<Image> images=new ArrayList<Image>();
        ButterKnife.inject(this);
        //初始化Bmob sdk
        Bmob.initialize(this, BMOB_APP_KEY);
        setContentView(R.layout.activity_upload);
        Button button_upload=(Button)findViewById(R.id.btn_upload);
        button_upload.setOnClickListener(new View.OnClickListener() {

          public void onClick(View view) {
//                //上传文件的代码
//                String filepath="/storage/emulated/0/InstaSave";
//                File file=new File(filepath);
//                String[] filePaths=new String[file.length()];
//                BmobFile bmobFile=new BmobFile(file);
//                bmobFile.upload(new UploadFileListener() {
//                    @Override
//                    public void done(BmobException e) {
//                        if (e==null){
//                            toast("上传成功");
//                        }
//                        else
//                            toast("上传失败"+e.getMessage());
//                    }
//                });
//            }

              String src=Environment.getExternalStorageDirectory() + "/" + "/Tencent/MicroMsg/fcd0479ad319fbde2b3b2ab1bf69970d/emoji/com.tencent.xin.emoticon.ali2";

              String dst=Environment.getExternalStorageDirectory() + "/emoji"+"/temp1.png";
             Thread thread=new Thread(new Runnable() {
                 @Override
                 public void run() {

                 }
             });
              new File(Environment.getExternalStorageDirectory() + "/emoji");

              File ff =  new File(src);
              //File childFolder = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM  + "/" + "emoji");
              File pngFile=new File(dst);
             // File ff =  new File(Environment.getExternalStorageDirectory() + "/" + "/InstaSave");
              File[] fs = ff.listFiles();
              String[] filePaths = new String[fs.length];
              if (fs != null && fs.length > 0) {
                  final int len = fs.length;
                  for (int i = 0; i < len; i++) {
                      filePaths[i] = fs[i].getAbsolutePath();
                      try {
                          copyFile(filePaths[i],dst);
                      } catch (Exception e) {
                          e.printStackTrace();
                      }

                      //File tempFile=new File(Environment.getExternalStorageDirectory() + "/emoji/" + "temp.png");
                      //pngFile.renameTo(tempFile);
                      filePaths[i]=pngFile.getAbsolutePath();
              }//获取该文件夹的所有文件的名字
                  BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
                      @Override
                      public void onSuccess(List<BmobFile> list, List<String> urls) {
                          Log.i("life", "insertBatchDatasWithMany -onSuccess :" + urls.size() + "-----" + "----" + urls);
                          if (urls.size() == len) {
                              toast("文件全部上传");
                              for (BmobFile bmobFile:list){

                                ImageFace imageFace=new ImageFace(bmobFile);
                                 // Log.i("a",bmobFile.getFilename());
                                imageFace.setFileName(bmobFile.getFilename());
                                  BmobACL acl = new BmobACL();  //创建ACL对象
                                  acl.setReadAccess(BmobUser.getCurrentUser(), true); // 设置当前用户可写的权限
                                  acl.setWriteAccess(BmobUser.getCurrentUser(), true); // 设置当前用户可写的权限

                                  imageFace.setACL(acl);    //设置这条数据的ACL信息
                                  imageFace.save(new SaveListener<String>() {
                                                     @Override
                                                     public void done(String s, BmobException e) {
                                                         if (e!=null){
                                                         Log.i("a",e.getMessage());}
                                                     }
                                                 }
                                  );
                              }

                          } else {
                              toast("文件上传不完整");

                          }
                      }


                      @Override
                      public void onProgress(int i, int i1, int i2, int i3) {
                          Log.i("life", "insertBatchDatasWithOne--onProgerss:" + i1 + "---" + i2 + "---" + i3);
                      }

                      @Override
                      public void onError(int i, String s) {
                          showToast("错误码" + i + "错误描述:" + s);
                      }
                  });


              }

          }
        });

    }
//    private List<String> ergodic(File file,List<String> resultFileName){
//        File[] files = file.listFiles();
//        if(files==null)return resultFileName;// 判断目录下是不是空的
//        for (File f : files) {
//            if(f.isDirectory()){// 判断是否文件夹
//                resultFileName.add(f.getPath());
//                ergodic(f,resultFileName);// 调用自身,查找子目录
//            }else
//                resultFileName.add(f.getPath());
//        }
//        return resultFileName;
//    }
//    public void copy(File src, File dst) throws IOException {
//        InputStream in = new FileInputStream(src);
//        OutputStream out = new FileOutputStream(dst);
//
//        // Transfer bytes from in to out
//        byte[] buf = new byte[1024];
//        int len;
//        while ((len = in.read(buf)) > 0) {
//            out.write(buf, 0, len);
//        }
//        in.close();
//        out.close();
//    }
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }


}


