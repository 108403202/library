package book_use;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;


public class DemoInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoInterceptor.class);//日誌

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在請求前執行，回傳值為布林值，如果是true則通過攔截器，false則不通過攔截器
        String authToken = request.getHeader("abc");
        if (authToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            LOGGER.info("Unauthorized access attempt, missing Authorization header.");
            return false;
        }
        if (!Objects.equals(authToken, "123")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            LOGGER.info("Unauthorized access attempt, invalid Authorization token.");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //請求處理完後執行
        LOGGER.info("Interceptor do postHandle ...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //請求及回應結束後執行
        LOGGER.info("Interceptor do afterCompletion ...");
    }
}
