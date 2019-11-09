package com.lr.best.mvp.base;


import com.lr.best.api.ApiManagerService;
import com.lr.best.di.component.DaggerMVPComponent;

import javax.inject.Inject;

/**
 * Model层的基类
 * 网络请求的配置
 */
public class BaseModel {
    //retrofit 请求数据的管理类
    @Inject
    public ApiManagerService mApiManagerService;

    public BaseModel() {
        DaggerMVPComponent.create().InjectinTo(this);
    }
}
