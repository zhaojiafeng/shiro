package com.spring_shiro.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dllo on 18/1/3.
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

//        验证异常类型，将异常信息发送到错误页面
        String message = e.getMessage();

        request.setAttribute("msg",message);

        try {
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request,response);

        } catch (ServletException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return new ModelAndView();
    }
}
