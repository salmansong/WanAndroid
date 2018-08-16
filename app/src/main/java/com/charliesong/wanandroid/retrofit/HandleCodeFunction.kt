package com.charliesong.wanandroid.retrofit

import com.charliesong.wanandroid.bean.BaseObjData
import io.reactivex.functions.Function

class  HandleCodeFunction<T>:Function<BaseObjData<T>,T>{
    override fun apply(t: BaseObjData<T>): T {
        if(t.errorCode<0){
            throw Exception(t.errorMsg)
        }
        return  t.data
    }

}