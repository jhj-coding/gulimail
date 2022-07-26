package com.jhj.gulimall.product.exception;

public class MyException extends RuntimeException{
    public MyException(String message) {
        super("我的自定义异常");
    }
}
