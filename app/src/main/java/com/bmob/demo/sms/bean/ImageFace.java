package com.bmob.demo.sms.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by liuteng on 2016/12/4.
 */

public class ImageFace extends BmobObject {
    private BmobFile img;
    private String fileName;
    public BmobFile getImg() {
        return img;
    }

    public void setImg(BmobFile img) {
        this.img = img;
    }
    public ImageFace(BmobFile bmobFile){
        img=bmobFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
