package filters;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.SessionBean;
import model.UserAccount;
import services.UserAccountService;

@WebFilter(urlPatterns = "*")
public class BlockedUsersFilter implements Filter {

    @Inject
    private SessionBean session;
    @Inject
    private UserAccountService userService;

    @Override
    public void doFilter(ServletRequest request,
	    ServletResponse response, FilterChain chain)
	    throws IOException, ServletException {
	// TODO Auto-generated method stub
	HttpServletRequest  httprequest =  (HttpServletRequest)  request;
	HttpServletResponse httpresponse = (HttpServletResponse) response;
	if (null != this.session.getUser()) {
	    UserAccount temp = this.userService.getById(this.session.getUser().getId());
	    if (temp.isBlocked()) {
		httprequest.getSession().invalidate();
		httpresponse.sendRedirect(httprequest.getContextPath() + "/home.xhtml");
		return;
	    } else {
		this.session.refresh();
		chain.doFilter(request, response);
		return;
	    }
	} else {
	    chain.doFilter(request, response);
	    return;
	}

    }

}
