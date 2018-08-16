package com.charliesong.wanandroid.bean;

@Deprecated
public class BaseListData<T> {

    /**
     * data : {"curPage":1112,"datas":[],"offset":22220,"over":true,"pageCount":70,"size":20,"total":1399}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean<T> data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


}
