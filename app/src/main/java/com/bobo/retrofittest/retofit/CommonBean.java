package com.bobo.retrofittest.retofit;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by bjmaoqisheng on 2016/11/3.
 */

public class CommonBean {

    private JsonElement data;
    private int status;
    private int code;
    private String msg;

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}