package com.njcool.lzccommon.view.photo.ui;

import com.njcool.lzccommon.view.photo.compress.CompressConfig;
import com.njcool.lzccommon.view.photo.model.CropOptions;
import com.njcool.lzccommon.view.photo.model.LubanOptions;
import com.njcool.lzccommon.view.photo.model.TakePhotoOptions;


/**
 * Created by chuan on 2017/7/19.
 */

public class CoolPhotoOptions {

    //裁切配置参数
    private boolean crop = true; //是否裁切
    private boolean cropTool = true; //裁切工具自带OR第三方
    private boolean cropStyle = true;// 裁切方式（尺寸/比例）  宽*高  or 宽/高
    private int cropHeight = 800, crop_width = 800;   //裁切宽度高度


    //压缩配置参数
    private boolean compressTool = true;  //压缩工具，自带or Luban
    private boolean compress = true;  //是否压缩
    private boolean compressProgess = true;  //是否显示压缩进度条
    private int compressMaxStorage = 102400;//压缩后大小不超过值
    private int compressWidth = 800, compressHeight = 800;  //压缩后图片大小
    private boolean compressRaw = true;  //拍照压缩后是否保存原图


    //选择照片配置
    private boolean pickTool = true;  //使用自带相册or not   tips:选择多张图片会自动切换到自带相册
    private boolean pickFrom = true;  //从哪选择图片，相册 or文件
    private int maxPhotos = 1; //最多选择图片数
    private boolean correctPhoto = true;  //纠正拍照图片的旋转角度


    public void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        if (pickTool) {
            builder.setWithOwnGallery(true);
        }
        if (correctPhoto) {
            builder.setCorrectImage(true);
        }
        takePhoto.setTakePhotoOptions(builder.create());

    }

    public void configCompress(TakePhoto takePhoto) {
        if (!compress) {
            takePhoto.onEnableCompress(null, false);
            return;
        }
        CompressConfig config;
        if (compressTool) {
            config = new CompressConfig.Builder()
                    .setMaxSize(compressMaxStorage)
                    .setMaxPixel(compressWidth >= compressHeight ? compressWidth : compressHeight)
                    .enableReserveRaw(compressRaw)
                    .create();
        } else {
            LubanOptions option = new LubanOptions.Builder()
                    .setMaxHeight(compressHeight)
                    .setMaxWidth(compressWidth)
                    .setMaxSize(compressMaxStorage)
                    .create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(compressRaw);
        }
        takePhoto.onEnableCompress(config, compressProgess);


    }

    public CropOptions getCropOptions() {
        if (!crop) return null;
        CropOptions.Builder builder = new CropOptions.Builder();

        if (cropStyle) {
            builder.setAspectX(crop_width).setAspectY(cropHeight);
        } else {
            builder.setOutputX(crop_width).setOutputY(cropHeight);
        }
        builder.setWithOwnCrop(cropTool);
        return builder.create();
    }

    public boolean isCrop() {
        return crop;
    }

    public void setCrop(boolean crop) {
        this.crop = crop;
    }

    public boolean isCropTool() {
        return cropTool;
    }

    public void setCropTool(boolean cropTool) {
        this.cropTool = cropTool;
    }

    public boolean isCropStyle() {
        return cropStyle;
    }

    public void setCropStyle(boolean cropStyle) {
        this.cropStyle = cropStyle;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
    }

    public int getCrop_width() {
        return crop_width;
    }

    public void setCrop_width(int crop_width) {
        this.crop_width = crop_width;
    }

    public boolean isCompressTool() {
        return compressTool;
    }

    public void setCompressTool(boolean compressTool) {
        this.compressTool = compressTool;
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public boolean isCompressProgess() {
        return compressProgess;
    }

    public void setCompressProgess(boolean compressProgess) {
        this.compressProgess = compressProgess;
    }

    public int getCompressMaxStorage() {
        return compressMaxStorage;
    }

    public void setCompressMaxStorage(int compressMaxStorage) {
        this.compressMaxStorage = compressMaxStorage;
    }

    public int getCompressWidth() {
        return compressWidth;
    }

    public void setCompressWidth(int compressWidth) {
        this.compressWidth = compressWidth;
    }

    public int getCompressHeight() {
        return compressHeight;
    }

    public void setCompressHeight(int compressHeight) {
        this.compressHeight = compressHeight;
    }

    public boolean isCompressRaw() {
        return compressRaw;
    }

    public void setCompressRaw(boolean compressRaw) {
        this.compressRaw = compressRaw;
    }

    public boolean isPickTool() {
        return pickTool;
    }

    public void setPickTool(boolean pickTool) {
        this.pickTool = pickTool;
    }

    public boolean isPickFrom() {
        return pickFrom;
    }

    public void setPickFrom(boolean pickFrom) {
        this.pickFrom = pickFrom;
    }

    public int getMaxPhotos() {
        return maxPhotos;
    }

    public void setMaxPhotos(int maxPhotos) {
        this.maxPhotos = maxPhotos;
    }

    public boolean isCorrectPhoto() {
        return correctPhoto;
    }

    public void setCorrectPhoto(boolean correctPhoto) {
        this.correctPhoto = correctPhoto;
    }
}
