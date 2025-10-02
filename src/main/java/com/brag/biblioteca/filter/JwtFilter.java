package com.brag.biblioteca.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.brag.biblioteca.config.FilterConfig;
import com.brag.biblioteca.util.JwtUtil;

@Component
public class JwtFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
			res.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(request, response);
			return;
		}

		String path = req.getRequestURI();
		if (path.startsWith("/api/auth")) {
			chain.doFilter(request, response);
			return;
		}

		String authHeader = req.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.getWriter().write("Se requiere autenticación");
			return;
		}

		String token = authHeader.substring(7);
		String user = JwtUtil.validateToken(token);

		if (user == null) {
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.getWriter().write("Token inválido o expirado");
			return;
		}

		req.setAttribute("user", user);

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
