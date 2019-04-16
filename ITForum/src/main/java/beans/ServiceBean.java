package beans;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserStatus;
import services.UserAccountService;
import services.UserStatusService;

@Named
@RequestScoped
public class ServiceBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6617075886417410997L;

    @Inject
    private UserAccountService userService;
    @Inject
    private UserStatusService userStatusService;
    private String Message = "Ожидайте...";
    
    
    @PostConstruct
    public void init() {
	HttpServletRequest request = (HttpServletRequest) FacesContext
		.getCurrentInstance().getExternalContext()
		.getRequest();
	HttpServletResponse response = (HttpServletResponse) FacesContext
		.getCurrentInstance().getExternalContext()
		.getResponse();
	UserStatus verified = userStatusService.getById(UserStatusService.Statuses.VERIFIED.getId());
	String userIdString = request.getParameter("uid");
	String verifyCode = request.getParameter("code");
	Integer userId = Integer.parseInt(userIdString);
	userService.verifyUser(userId, verifyCode, verified);
	try {
	    response.sendRedirect(request.getContextPath() + "/home.xhtml");
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }


    /**
     * @return the message
     */
    public String getMessage() {
	return Message;
    }


    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
	Message = message;
    }
}
