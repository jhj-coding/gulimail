//package com.jhj.gulimall.auth.server.interceptor;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@Component
//public class MyOmterceptor implements HandlerInterceptor {
//
//    /**
//     * 目标方法前执行
//     * @param request
//     * @param response
//     * @param handler
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session=request.getSession();
//        Object redis_user = session.getAttribute("redis_user");
//        if (redis_user==null){
//            //没登陆
//        }else{
//            //登录
//        }
//        return false;
//    }
//}
