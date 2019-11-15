package com.ebanswers.kitchendiary.network.observer;

/**
 * Created by DeMon on 2017/9/6.
 */

public interface ObserverOnNextListener<T,K> {

    void onNext(T t);

    void onError(K k);


}
