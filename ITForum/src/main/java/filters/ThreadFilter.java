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

import services.ThreadService;

@WebFilter(urlPatterns = {"/v2/thread.xhtml"})
public class ThreadFilter implements Filter {

    @Inject
    private ThreadService threadService;
    
    @Override
    public void doFilter(ServletRequest request,
	    ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	HttpServletRequest  httprequest =  (HttpServletRequest)  request;
	HttpServletResponse httpresponse = (HttpServletResponse) response;
	if(httprequest.getHeader("X-Requested-With") != null) {
	    chain.doFilter(request, response);
	    return;
	}
	String id = httprequest.getParameter("id");
	if(null == id) {
	    httpresponse.sendRedirect(httprequest.getContextPath() + "/v2/error.xhtml");
	    return;
	}
	if(!StringUtils.isNumeric(id)) {
	    httpresponse.sendRedirect(httprequest.getContextPath() + "/v2/error.xhtml");
	    return;
	}
	if(null == threadService.getById(Integer.parseInt(id))){
	    httpresponse.sendRedirect(httprequest.getContextPath() + "/v2/error.xhtml");
	    return;
	}
	chain.doFilter(request, response);
	return;
    }
}
