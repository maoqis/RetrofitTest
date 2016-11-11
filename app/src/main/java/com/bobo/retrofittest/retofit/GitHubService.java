package com.bobo.retrofittest.retofit;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;


public interface GitHubService{
  @GET("/api/home/v2/recommendVideo.do")
  Call<CommonBean> listCommonBean();

  @GET("/api/home/v2/recommendVideo.do")
  Observable<CommonBean> listCommonBeanRxJava();
  Flowable<CommonBean> listCommonBeanRxJavaFlow();

}