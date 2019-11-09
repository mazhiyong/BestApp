package com.lr.best.di.component;

import com.lr.best.di.scope.ActivityScope;

import javax.inject.Singleton;

import dagger.Component;

@ActivityScope
@Singleton
@Component(modules = com.lr.best.di.module.ObjectModule.class)
public interface ObjectComponent {
   // void injectTo(PayHistoryAdapter adapter);
}
