package Controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle
            (HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //System.out.println("Pre Handle method is Calling");

        System.out.println("raw cookie = " + request.getHeader("Cookie"));

        /*System.out.println(request.getCookies()[0]);
        for (Cookie cookie : request.getCookies()){
            cookie.getName();
        }*/
        return true;
    }
}

