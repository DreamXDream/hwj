package controller;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor implements HandlerInterceptor {

    private final String ADMINSESSION = "admin";

    //拦截钱处理

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        Object sessionObj = request.getSession().getAttribute(ADMINSESSION);

        if(sessionObj==""||sessionObj==null) {
            System.out.println("拦截1:"+sessionObj);

            response.getWriter().print("er");

            return false;
        }

          return true;
    }
    //拦截后处理
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav) throws Exception {

    }
    //全部完成后处理
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) throws Exception { }

}


   class AdminInterceptors implements HandlerInterceptor {

    private final String ADMINSESSION = "loginname";

    //拦截钱处理

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        Object sessionObj = request.getSession().getAttribute(ADMINSESSION);

        if(sessionObj==""||sessionObj==null) {
            System.out.println("拦截1:"+sessionObj);

            response.getWriter().print("er");

            return false;
        }

        return true;

    }
    //拦截后处理
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav) throws Exception {

    }
    //全部完成后处理
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) throws Exception { }

}



