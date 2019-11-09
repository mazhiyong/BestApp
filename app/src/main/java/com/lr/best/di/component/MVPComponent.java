package com.lr.best.di.component;
import com.lr.best.di.module.MVPModule;
import com.lr.best.mvp.base.BaseModel;
import com.lr.best.mvp.model.RequestModelImp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * MVP的DaggeR的注射器
 */

@Singleton
@Component(modules = MVPModule.class)
public interface MVPComponent {

    //ApiManagerService
    void InjectinTo(BaseModel baseModel);
    //CompositeDisposable
    void InjectinTo(RequestModelImp requestModel);

}
