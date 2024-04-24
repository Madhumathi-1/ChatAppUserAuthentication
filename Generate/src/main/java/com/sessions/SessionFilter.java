package com.sessions;

import java.io.IOException;
//import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionFilter implements Filter {

	private String loginPage = "login.jsp";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String sessionId = getSessionIdFromCookie(httpRequest);

		if (sessionId != null) { 
			if (SessionUtils.isValidSession(sessionId)) {
				SessionUtils.updateSessionActivity(sessionId);
				httpResponse.sendRedirect("homepage.jsp");
			}
			httpResponse.sendRedirect(loginPage);
		} 
		chain.doFilter(request, response);
	}
	public static String getSessionIdFromCookie(HttpServletRequest request) {
	    Cookie[] cookies = request.getCookies();
	    
	    String sessionId = null;
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("session_id".equals(cookie.getName())) {
	                sessionId = cookie.getValue();
	                break;
	            }
	        }
	    }
	    return sessionId;
	}

	@Override
	public void destroy() {
		
	}
}
