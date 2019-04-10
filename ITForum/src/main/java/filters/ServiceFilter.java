package filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import services.UserAccountService;

@WebFilter(urlPatterns = { "/service.xhtml" })
public class ServiceFilter implements Filter {

    @Inject
    private UserAccountService userService;

    @Override
    public void doFilter(ServletRequest request,
	    ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	// TODO Auto-generated method stub
	HttpServletRequest httprequest = (HttpServletRequest) request;
	HttpServletResponse httpresponse = (HttpServletResponse) response;
	String id = httprequest.getParameter("uid"),
		code = httprequest.getParameter("code");
	if (null == code) {
	    httpresponse.sendRedirect(
		    httprequest.getContextPath() + "/home.xhtml");
	    return;
	}
	if (null == id) {
	    httpresponse.sendRedirect(
		    httprequest.getContextPath() + "/home.xhtml");
	    return;
	}
	if (!StringUtils.isNumeric(id)) {
	    httpresponse.sendRedirect(
		    httprequest.getContextPath() + "/home.xhtml");
	    return;
	}
	if (null == userService.getById(Integer.parseInt(id))) {
	    httpresponse.sendRedirect(
		    httprequest.getContextPath() + "/home.xhtml");
	    return;
	}
	chain.doFilter(request, response);
	return;

    }

}
