package com.southcn.nfapp.ncov.advice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.southcn.nfapp.ncov.enums.MsgCode;
import com.southcn.nfapp.ncov.exception.SouthcnException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView allExceptionHandler(Exception exception) {
        FastJsonJsonView view = new FastJsonJsonView();
        Map<String, Object> attributes = new HashMap<>();
        if (exception instanceof SouthcnException) {
            SouthcnException southcnException = (SouthcnException) exception;
            attributes.put("code", southcnException.getCode().getCode());
            attributes.put("msg", southcnException.getMsg());
            attributes.put("success", Boolean.FALSE);
            attributes.put("data", southcnException.getData());
        } else {
            attributes.put("code", MsgCode.FAIL.getCode());
            attributes.put("msg", MsgCode.FAIL.getCode());
            attributes.put("success", Boolean.FALSE);
        }
        view.setAttributesMap(attributes);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(view);
        return modelAndView;
    }
}
