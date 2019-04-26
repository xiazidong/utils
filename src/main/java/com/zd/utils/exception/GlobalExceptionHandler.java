package com.zd.utils.exception;

import com.alibaba.fastjson.JSONException;
import com.sf.pg.constant.ErrorStatusEnum;
import com.sf.pg.core.BaseExceptionHandler;
import com.sf.pg.entity.R;
import com.sf.pg.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 全局异常处理类
 *
 * @author lijie.zh
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseExceptionHandler {

    @Value("${system.enableFriendlyError:false}")
    private boolean isEnableFriendlyError;

    /**
     * 针对未知异常类获取错误内容
     * @param exception
     * @return
     */
    @Override
    protected R gerUnkownExceptionMessage(Exception exception) {
        //如果启用友好提示，针对未知的不能捕获的异常不显示详细堆栈信息，一般用于生产环境
       if (isEnableFriendlyError) {
           return R.error("系统错误，请稍后再试！");
       }
        String message = exception.getMessage();
        if (StringUtils.isBlank(message)) {
            message = null != exception.getCause() ? exception.getCause().getMessage() : "未知错误";
        }
        return R.error(message);
    }

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Object methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        List<String> invalidArguments = new ArrayList();
        Iterator var3 = exception.getBindingResult().getAllErrors().iterator();

        while(var3.hasNext()) {
            ObjectError error = (ObjectError)var3.next();
            StringBuilder msg = new StringBuilder();
            StringBuilder field = new StringBuilder();
            if (error instanceof FieldError) {
                if (error.contains(TypeMismatchException.class)) {
                    field.append(((FieldError)error).getField());
                    msg.append("类型不匹配");
                } else {
                    field.append(((FieldError)error).getField());
                    msg.append(error.getDefaultMessage());
                }

                log.warn("defaultMessage:{}, field:{}, rejectedValue:{} ", new Object[]{error.getDefaultMessage(), ((FieldError)error).getField(), ((FieldError)error).getRejectedValue()});
            } else {
                field.append(error.getObjectName());
                msg.append(error.getDefaultMessage());
                log.warn("defaultMessage:{}, object:{} ", new Object[]{error.getDefaultMessage(), error.getObjectName(), error.getCode()});
            }

            if (field.length() > 0) {
                field.append(":");
            }

            msg.insert(0, field);
            invalidArguments.add(msg.toString());
        }

        String errorStr = StringUtils.join(invalidArguments, ",");
        return R.error(errorStr, ErrorStatusEnum.INVALID_ARGUMENTS.getValue());
    }

    /**
     * 业务异常处理
     *
     * @param exception
     * @return
     */
    @Override
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public Object businessExceptionHandler(BusinessException exception) {
        Throwable throwable = findRootExceptionThrowable(exception);

        if (throwable != null && !(throwable instanceof BusinessException)) {
            log.debug("业务异常: {}, {}", exception.getErrMsg(), exception);
        } else {
            log.debug("业务异常: {}, {}", exception.getErrMsg(), exception.getStackTrace().length > 0 ? exception.getStackTrace()[0] : "");
        }

        return R.error(exception.getErrMsg(), StringUtils.isBlank(exception.getErrCode()) ? ErrorStatusEnum.BUSINESS_ERROR.getValue() : exception.getErrCode());
    }

    @ResponseBody
    @ExceptionHandler(value = {JSONException.class, HttpMessageNotReadableException.class})
    public Object jsonError() {
        return R.error("请传递正确的JSON格式参数", "-1");
    }

    @ResponseBody
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public Object httpMediaTypeNotSupportedError(HttpMediaTypeNotSupportedException e) {
        return R.error("不支持当前content-type", "-1");
    }
}