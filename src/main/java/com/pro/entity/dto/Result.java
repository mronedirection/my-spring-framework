package com.pro.entity.dto;

import lombok.Data;

/**
 * @author RenHao
 * @create 2023-08-14 21:09
 */
@Data
public class Result<T> {
    //本次请求结果的状态码，200表示成功
    private int code;
    //本次请求结果的详情
    private String msg;
    //本次请求返回的结果集
    private T data;
}
