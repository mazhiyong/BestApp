package com.lr.best.di.component;


import com.lr.best.basic.BasicActivity;
import com.lr.best.basic.BasicFragment;
import com.lr.best.di.module.PersenerModule;
import com.lr.best.di.scope.ActivityScope;

import javax.inject.Singleton;

import dagger.Component;


@ActivityScope
@Singleton
@Component(modules = PersenerModule.class)
public interface PersenerComponent {
    //RequestPresenterImp
    void injectTo(BasicActivity activity);
    void injectTo(BasicFragment fragment);

}
