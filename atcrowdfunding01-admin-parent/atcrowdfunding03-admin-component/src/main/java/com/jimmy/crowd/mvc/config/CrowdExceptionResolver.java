package com.jimmy.crowd.mvc.config;

import com.google.gson.Gson;
import com.jimmy.crowd.constant.CrowdConstant;
import com.jimmy.crowd.exception.LoginFailedException;
import com.jimmy.crowd.util.CrowdUtil;
import com.jimmy.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ControllerAdvice 表示当前类是一个基于注解的异常处理器类
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveMathException(LoginFailedException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonResolve(viewName, e, request, response);
    }
    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resolveMathException(ArithmeticException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, e, request, response);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveMathException(NullPointerException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, e, request, response);
    }


    private ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.判断当前请求类型
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        //2.如果是ajax请求
        if (judgeResult) {
            //3.创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            //4.创建Gson对象
            Gson gson = new Gson();
            //5.将resultEntity对象转换为json字符串
            String json = gson.toJson(resultEntity);
            //6.将json字符串作为响应体返回给浏览器
            response.getWriter().write(json);
            //7.由于在response对象返回了响应，所以不提供ModelAndView对象
            return null;

        }

        //8.如果不是Ajax请求，则先创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();

        //9.将exception存入模型
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        //10.设置对应的视图名称

        modelAndView.setViewName(viewName);

        //11.返回modelAndView对象
        return modelAndView;
    }
}
