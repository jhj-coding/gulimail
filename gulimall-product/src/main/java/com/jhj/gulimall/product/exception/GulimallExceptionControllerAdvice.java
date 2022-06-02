package com.jhj.gulimall.product.exception;

import com.jhj.common.exception.BizCodeEnume;
import com.jhj.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description GulimallExceptionControllerAdvice
 * @Author jhj
 * @Date 2022/6/110:34
 **/
@Slf4j
//处理哪个包下的异常
@RestControllerAdvice(basePackages = "com.jhj.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {

    //校验异常
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildExceptinon(MethodArgumentNotValidException e){
        log.error("检验错误{}",e.getMessage());
        HashMap<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((res)->{
            errorMap.put(res.getField(),res.getDefaultMessage());
        });
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(), BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data",errorMap);
    }

    //校验异常
    @ExceptionHandler(value = Throwable.class)
    public R handleVaildExceptinon(Throwable e){
        log.error(e.getMessage());
        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(), BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }
}
