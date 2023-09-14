package com.open.eoss.web.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EossRes<T> {
    boolean success = false;
    private int errCode;
    private String errMsg;

    //对于分页数据，total有实际值
    //对于其它数据，total为0
    long total = 0;

    //对于分页数据，data是一个List
    //对于其它数据，data是一个POJO
    T data;

    public static <T> EossRes<T> success(){
        EossRes<T> res = new EossRes<>();
        res.success = true;
        return res;
    }

    public static <T> EossRes<T> success(T data){
        EossRes<T> res = new EossRes<>();
        res.data = data;
        res.success = true;
        return res;
    }

    public static <T> EossRes<T> success(long total, T data){
        EossRes<T> res = new EossRes<>();
        res.total = total;
        if(data == null){
            //前端要求，如果返回的是列表，在没有数据的情况下，需要返回 []，而不是 null
            res.data = (T)new LinkedList();
        }else{
            res.data = data;
        }
        res.success = true;
        return res;
    }

    public static <T> EossRes<T> failed(int errorCode, String errorDesc){
        EossRes<T> res = new EossRes<>();
        res.success = false;
        res.errCode = errorCode;
        res.errMsg = errorDesc;
        return res;
    }
}