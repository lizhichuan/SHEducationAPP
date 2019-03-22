package com.njcool.lzccommon.common;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.njcool.lzccommon.R;
import com.njcool.lzccommon.view.photo.ui.CoolPhotoOptions;
import com.njcool.lzccommon.view.photo.ui.TakePhoto;

import java.io.File;


/**
 * Created by chuan on 2017/7/4.
 */

public class CoolPhotoPop {

    public CoolPhotoPop() {
    }

    private PopupWindow pop;
    private CoolPhotoOptions coolPhotoOptions;

    private PopClickListener popClickListener;

    public PopClickListener getPopClickListener() {
        return popClickListener;
    }

    public void setPopClickListener(PopClickListener popClickListener) {
        this.popClickListener = popClickListener;
    }

    public void ShowPop(Activity activity, View rootView, int gravity, final TakePhoto takePhoto, CoolPhotoOptions mcoolPhotoOptions) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);

        coolPhotoOptions = mcoolPhotoOptions;
        coolPhotoOptions.configCompress(takePhoto);
        coolPhotoOptions.configTakePhotoOption(takePhoto);

        View view = activity.getLayoutInflater().inflate(R.layout.pop_select_photos, null);
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.parent);
        TextView tv_camera = (TextView) view.findViewById(R.id.tv_camera);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_photo = (TextView) view.findViewById(R.id.tv_photo);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                if (popClickListener != null) {
                    popClickListener.onCancel();
                }

            }
        });
        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                if (popClickListener != null) {
                    popClickListener.onCamera();

                }
                if (coolPhotoOptions.isCrop()) {
                    takePhoto.onPickFromCaptureWithCrop(imageUri, coolPhotoOptions.getCropOptions());
                } else {
                    takePhoto.onPickFromCapture(imageUri);
                }
            }
        });
        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                if (popClickListener != null) {
                    popClickListener.onGallery();
                }
                int limit = coolPhotoOptions.getMaxPhotos();
                if (limit > 1) {
                    if (coolPhotoOptions.isCrop()) {
                        takePhoto.onPickMultipleWithCrop(limit, coolPhotoOptions.getCropOptions());
                    } else {
                        takePhoto.onPickMultiple(limit);
                    }
                    return;
                }
                if (!coolPhotoOptions.isPickFrom()) {
                    if (coolPhotoOptions.isCrop()) {
                        takePhoto.onPickFromDocumentsWithCrop(imageUri, coolPhotoOptions.getCropOptions());
                    } else {
                        takePhoto.onPickFromDocuments();
                    }
                    return;
                } else {
                    if (coolPhotoOptions.isCrop()) {
                        takePhoto.onPickFromGalleryWithCrop(imageUri, coolPhotoOptions.getCropOptions());
                    } else {
                        takePhoto.onPickFromGallery();
                    }
                }
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popClickListener != null) {
                    popClickListener.onCancel();
                }
                pop.dismiss();
            }
        });
        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAtLocation(rootView, gravity, 0, 0);
    }

    public void ShowPop(Activity activity, View rootView, int gravity, final TakePhoto takePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);

        coolPhotoOptions = new CoolPhotoOptions();
        coolPhotoOptions.configCompress(takePhoto);
        coolPhotoOptions.configTakePhotoOption(takePhoto);

        View view = activity.getLayoutInflater().inflate(R.layout.pop_select_photos, null);
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.parent);
        TextView tv_camera = (TextView) view.findViewById(R.id.tv_camera);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_photo = (TextView) view.findViewById(R.id.tv_photo);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                if (popClickListener != null) {
                    popClickListener.onCancel();
                }

            }
        });
        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                if (popClickListener != null) {
                    popClickListener.onCamera();

                }
                if (coolPhotoOptions.isCrop()) {
                    takePhoto.onPickFromCaptureWithCrop(imageUri, coolPhotoOptions.getCropOptions());
                } else {
                    takePhoto.onPickFromCapture(imageUri);
                }
            }
        });
        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                if (popClickListener != null) {
                    popClickListener.onGallery();
                }
                int limit = coolPhotoOptions.getMaxPhotos();
                if (limit > 1) {
                    if (coolPhotoOptions.isCrop()) {
                        takePhoto.onPickMultipleWithCrop(limit, coolPhotoOptions.getCropOptions());
                    } else {
                        takePhoto.onPickMultiple(limit);
                    }
                    return;
                }
                if (!coolPhotoOptions.isPickFrom()) {
                    if (coolPhotoOptions.isCrop()) {
                        takePhoto.onPickFromDocumentsWithCrop(imageUri, coolPhotoOptions.getCropOptions());
                    } else {
                        takePhoto.onPickFromDocuments();
                    }
                    return;
                } else {
                    if (coolPhotoOptions.isCrop()) {
                        takePhoto.onPickFromGalleryWithCrop(imageUri, coolPhotoOptions.getCropOptions());
                    } else {
                        takePhoto.onPickFromGallery();
                    }
                }
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popClickListener != null) {
                    popClickListener.onCancel();
                }
                pop.dismiss();
            }
        });
        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.showAtLocation(rootView, gravity, 0, 0);
    }


    public interface PopClickListener {
        public void onCamera();

        public void onGallery();

        public void onCancel();
    }

}
