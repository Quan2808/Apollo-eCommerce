package apollo.clients.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String jwtToken = (String) session.getAttribute("jwtToken");
        String role = (String) session.getAttribute("role");

        // Check if the request is for the admin or shipper login pages
        String uri = request.getRequestURI();
        String authParam = request.getParameter("auth");

        // Redirect to welcome page if trying to access login pages directly without auth param
        if ((uri.equals("/dashboard/signin") || uri.equals("/shipper/signin")) && !"true".equals(authParam)) {
            response.sendRedirect("/");
            return false;
        }

        // Check if the user has the appropriate role for the requested URL
        if (uri.startsWith("/dashboard") && !"ADMIN".equals(role)) {
            response.sendRedirect("/");
            return false;
        }

        if (uri.startsWith("/shipper") && !"SHIPPER".equals(role)) {
            response.sendRedirect("/");
            return false;
        }

        // Redirect to welcome page if no valid JWT token
        if (jwtToken == null || jwtToken.isEmpty()) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
