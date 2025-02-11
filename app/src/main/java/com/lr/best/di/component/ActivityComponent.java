package com.lr.best.di.component;


import com.lr.best.di.module.ActivityModule;
import com.lr.best.di.scope.ActivityScope;

import dagger.Component;

/**
 * DarggeR中 Activity的注射器
 */

@ActivityScope
//继承AppComponent  依赖Module层
@Component(dependencies = {AppComponent.class},modules = {ActivityModule.class})
public interface ActivityComponent {
    //void  inject(BaseActivity mainActivity);
}
