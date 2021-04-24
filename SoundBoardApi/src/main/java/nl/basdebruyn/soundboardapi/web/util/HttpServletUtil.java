package nl.basdebruyn.soundboardapi.web.util;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpServletUtil {
    public static void setResponseAsUnauthorized(HttpServletResponse response, String reason) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(reason);
        response.getWriter().flush();
    }
}
