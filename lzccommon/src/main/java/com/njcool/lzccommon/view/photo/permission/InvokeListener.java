package com.njcool.lzccommon.view.photo.permission;


import com.njcool.lzccommon.view.photo.model.InvokeParam;

/**
 * 授权管理回调
 */
public interface InvokeListener {
    PermissionManager.TPermissionType invoke(InvokeParam invokeParam);
}
