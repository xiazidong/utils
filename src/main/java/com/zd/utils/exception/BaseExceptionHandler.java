package com.zd.utils.exception;

import com.sf.pg.constant.ErrorStatusEnum;
import com.sf.pg.entity.R;
import com.sf.pg.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Zidong
 * @date 2019/4/23 11:28 AM
 */
public abstract class BaseExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);

    public BaseExceptionHandler() {
    }

    @ResponseBody
    @ExceptionHandler({ConstraintViolationException.class})
    public Object constraintViolationException(ConstraintViolationException exception) {
        List<String> invalidArguments = new ArrayList();
        Set<ConstraintViolation<?>> errors = exception.getConstraintViolations();
        Iterator var4 = errors.iterator();

        while (var4.hasNext()) {
            ConstraintViolation<?> violation = (ConstraintViolation) var4.next();
            String field = ((PathImpl) violation.getPropertyPath()).getLeafNode().toString();
            invalidArguments.add(field + ":" + violation.getMessage());
        }

        String errorStr = StringUtils.join(invalidArguments, ",");
        return R.error(errorStr, ErrorStatusEnum.INVALID_ARGUMENTS.getValue());
    }

    @ResponseBody
    @ExceptionHandler({BindException.class})
    public Object methodArgumentNotValidHandler(BindException exception) {
        List<String> invalidArguments = new ArrayList();
        Iterator var3 = exception.getBindingResult().getAllErrors().iterator();

        while (var3.hasNext()) {
            ObjectError error = (ObjectError) var3.next();
            StringBuffer msg = new StringBuffer();
            StringBuffer field = new StringBuffer();
            if (error instanceof FieldError) {
                if (error.contains(TypeMismatchException.class)) {
                    field.append(((FieldError) error).getField());
                    msg.append("类型不匹配");
                } else {
                    field.append(((FieldError) error).getField());
                    msg.append(error.getDefaultMessage());
                }

                log.warn("defaultMessage:{}, field:{}, rejectedValue:{} ", new Object[]{error.getDefaultMessage(), ((FieldError) error).getField(), ((FieldError) error).getRejectedValue()});
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

    @ResponseBody
    @ExceptionHandler({ServletRequestBindingException.class, MethodArgumentTypeMismatchException.class})
    public Object servletArgumentNotValidHandler(ServletRequestBindingException exception) {
        return R.error(exception.getMessage(), ErrorStatusEnum.INVALID_ARGUMENTS.getValue());
    }

    @ResponseBody
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Object httpRequestMethodNotValidHandler(HttpRequestMethodNotSupportedException exception) {
        return R.error(exception.getMessage(), ErrorStatusEnum.FORBIDDEN_REQUEST.getValue());
    }

    @ResponseBody
    @ExceptionHandler({BusinessException.class})
    public Object businessExceptionHandler(BusinessException exception) {
        Throwable throwable = this.findRootExceptionThrowable(exception);
        if (throwable != null && !(throwable instanceof BusinessException)) {
            log.error("业务异常: {}, {}", exception.getErrMsg(), exception);
        } else {
            log.warn("业务异常: {}, {}", exception.getErrMsg(), exception.getStackTrace().length > 0 ? exception.getStackTrace()[0] : "");
        }

        return R.error(exception.getErrMsg(), StringUtils.isBlank(exception.getErrCode()) ? ErrorStatusEnum.BUSINESS_ERROR.getValue() : exception.getErrCode());
    }

    protected Throwable findRootExceptionThrowable(Exception e) {
        Object rootCause;
        for (rootCause = e; ((Throwable) rootCause).getCause() != null; rootCause = ((Throwable) rootCause).getCause()) {
            ;
        }

        return (Throwable) rootCause;
    }

    @ResponseBody
    @ExceptionHandler({MyBatisSystemException.class})
    public Object myBatisSystemExceptionHandler(MyBatisSystemException exception) {
        log.error("mybatis异常", exception);
        return this.gerUnkownExceptionMessage(exception);
    }

    @ResponseBody
    @ExceptionHandler({DuplicateKeyException.class})
    public Object dataIntegrityViolationException(DuplicateKeyException exception) {
        log.error("数据库异常", exception);
        return R.error("主键冲突或违反唯一约束", ErrorStatusEnum.INVALID_ARGUMENTS.getValue());
    }

    @ResponseBody
    @ExceptionHandler({DataIntegrityViolationException.class})
    public Object dataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error("数据库异常", exception);
        return R.error("内容超长或者数据异常", ErrorStatusEnum.INVALID_ARGUMENTS.getValue());
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Object unknownExceptionHandler(Exception exception) {
        log.error("系统异常", exception);
        return this.gerUnkownExceptionMessage(exception);
    }

    @ResponseBody
    @ExceptionHandler({UnauthorizedException.class})
    public Object authorizationException() {
        return R.error(ErrorStatusEnum.INSUFFICIENT_USER_PERMISSIONS.getText(), ErrorStatusEnum.INSUFFICIENT_USER_PERMISSIONS.getValue());
    }

    protected R gerUnkownExceptionMessage(Exception exception) {
        String message = exception.getMessage();
        return R.error(message, ErrorStatusEnum.UNKNOWN_ERRROR.getValue());
    }
}