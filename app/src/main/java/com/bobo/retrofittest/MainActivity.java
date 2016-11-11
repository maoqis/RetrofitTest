package com.bobo.retrofittest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.bobo.retrofittest.retofit.CommonBean;
import com.bobo.retrofittest.retofit.GitHubService;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.victoralbertos.rxlifecycle_interop.Rx2Activity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Rx2Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String host = "your host";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
//        testRetrofit(service);
        testRetrofitRxJava(service);

        finish();

    }

    private void testApi() {

    }

    private void testRetrofitRxJava(GitHubService service) {
        Observable<CommonBean> commonBeanObservable = service.listCommonBeanRxJava();
        Observer<CommonBean> subscriber = new Observer<CommonBean>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
            }

            @Override
            public void onNext(CommonBean commonBean) {
                Log.d(TAG, "onNext() called with: commonBean = [" + commonBean + "]");
            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete() called");
            }

        };
        commonBeanObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(this.<CommonBean>bindUntilEvent2x(ActivityEvent.DESTROY, BackpressureStrategy.DROP))
                .observeOn(Schedulers.io())
                .subscribe(subscriber);
        ;
    }



    Subscriber<CommonBean> subscriber = new Subscriber<CommonBean>() {

        @Override
        public void onSubscribe(Subscription s) {

        }

        @Override
        public void onNext(CommonBean commonBean) {
            Log.d(TAG, "onNext() called with: commonBean = [" + commonBean + "]");
        }

        @Override
        public void onError(Throwable t) {
            Log.e(TAG, "onError: ", t);
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete() called");
        }

    };

    private void testRetrofitRxJavaF(GitHubService service) {
        Flowable<CommonBean> commonBeanObservable = service.listCommonBeanRxJavaFlow();
        commonBeanObservable.
                subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
        commonBeanObservable.
//                指定 subscribe()所发生的线程，即 Observable.OnSubscribe被激活时所处的线程。或者叫做事件产生的线程。
        subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
//        observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
                .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    private void testRetrofit(GitHubService service) {
        Call<CommonBean> listCall = service.listCommonBean();
        listCall.enqueue(new Callback<CommonBean>() {
            @Override
            public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                if (response.isSuccessful()) {
                    CommonBean body = response.body();
                    Log.d(TAG, "onResponse: body=" + body);

                }
            }

            @Override
            public void onFailure(Call<CommonBean> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }


        });
    }
}
