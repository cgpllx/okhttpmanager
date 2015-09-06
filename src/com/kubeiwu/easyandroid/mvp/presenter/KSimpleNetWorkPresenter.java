package com.kubeiwu.easyandroid.mvp.presenter;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.os.Bundle;

import com.kubeiwu.easyandroid.mvp.kabstract.KRxJavaPresenter;
import com.kubeiwu.easyandroid.mvp.view.ISimpleNetWorkView;

public class KSimpleNetWorkPresenter<T> extends KRxJavaPresenter<ISimpleNetWorkView<T>, T> {

	// observable.cache() //观察者 会回调多次，但是只会调用一次网络
	public void loadData(Bundle bundle) {
		Observable<T> observable = creatObservable(bundle).subscribeOn(Schedulers.io())//
				.observeOn(AndroidSchedulers.mainThread());
		if (observable == null) {
			throw new IllegalArgumentException("please Override onCreatObservable method, And can not be null，");
		}
		cancel();// 先取消之前的事件
		subscriber = new KSubscriber(this.mController);
		observable.subscribe(subscriber);
	}
	public void loadData() {
		loadData(null);
	}

	@Override
	public Observable<T> creatObservable(Bundle bundle) {
		return getView().onCreatObservable(bundle);
	}

}