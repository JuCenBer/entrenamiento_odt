package cespi.induccion.estacionamiento.filters;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Enumeration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;

import cespi.induccion.estacionamiento.services.AuthorizationService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(
		filterName="jwt-auth-filter", 
		urlPatterns="*"
)
@Component
public class JWTAuthenticationFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if("/parking-back/auth/login".equals(req.getRequestURI()) 
				|| "/parking-back/auth/register".equals(req.getRequestURI()) 
				|| HttpMethod.OPTIONS.matches(req.getMethod())) {
			chain.doFilter(request, response);
			return ;
		}
		Enumeration<String> headers = req.getHeaderNames();
		if (headers != null) {
	        while (headers.hasMoreElements()) {
	            String name = headers.nextElement();
	            System.out.println("Header: " + name + " value:" + req.getHeader(name));
	        }
	    }
		String token = req.getHeader(HttpHeaders.AUTHORIZATION);
		System.out.println(token);
		if ((token == null) || !AuthorizationService.validateToken(token)) {
			HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.setHeader("Access-Control-Allow-Origin", "*");
			return;
		}
		chain.doFilter(request, response);
	}

}
