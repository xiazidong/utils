package com.zd.utils.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 切面-请求、响应参数修改
 * @author Zidong
 * @date 2019/4/26 10:14 AM
 */
@Aspect
@Component
@Slf4j
public class ParamAspect {

    private final String ExpGetResultDataPonit = "execution(* com.sf.controller.LoginController.*(..))";


    @Pointcut("execution(* com.sf..*())")
    public void doOperation() {
        log.info("参数检验AOP");
    }

    /**
     * 环绕通知：
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    @Around(ExpGetResultDataPonit)
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        System.out.println("环绕通知的目标方法名：" + proceedingJoinPoint.getSignature().getName());
        processInputArg(proceedingJoinPoint.getArgs());
        try {//obj之前可以写目标方法执行前的逻辑
            //调用执行目标方法
            Object obj = proceedingJoinPoint.proceed();
            processOutPutObj(obj);
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    /**
     * 处理返回对象
     */
    private void processOutPutObj(Object obj) {
        System.out.println("OBJ 原本为：" + obj.toString());
        // if (obj instanceof ResultVO) {
        //     ResultVO resultVO = (ResultVO) obj;
        //     resultVO.setMessage("哈哈，我把值修改了" + resultVO.getMessage());
        //     System.out.println(resultVO);
        // }
    }

    /**
     * 处理输入参数
     *
     * @param args 入参列表
     */
    private void processInputArg(Object[] args) {
        for (Object arg : args) {
            System.out.println("ARG原来为:" + arg);
            // if (arg instanceof ParamVO) {
            //     ParamVO paramVO = (ParamVO) arg;
            //     paramVO.setInputParam("654321");
            // }
        }
    }

}