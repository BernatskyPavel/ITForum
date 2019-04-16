package beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import model.UserAccount;
import services.UserAccountService;
import services.UserStatusService;

@Named
@SessionScoped
public class SessionBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -124466934030612288L;
    @Inject
    private UserAccountService userAccountService;
    private UserAccount tempUser;
    private Argon2 hasher;
    @NotNull(message = "Поле не может быть пустым")
    private String login;
    @NotNull(message = "Поле не может быть пустым")
    private String password;
    private UserAccount user;
    private boolean isLogin;
    @RequestScoped
    private String message;

    @PostConstruct
    public void init() {
	isLogin = false;
	user = null;
	hasher = Argon2Factory.create(Argon2Types.ARGON2id);
    }

    public void tryLogIn() {
	if (this.tempUser == null) {
	    this.tempUser = userAccountService.getByLogin(this.login);
	} else {
	    if (!tempUser.getUsername().equals(this.login)
		    && !tempUser.getEmail().equals(this.login))
		this.tempUser = userAccountService
			.getByLogin(this.login);
	}
	if (ObjectUtils.allNotNull(tempUser)) {
	    if (tempUser.getUserStatusBean().getId().equals(
		    UserStatusService.Statuses.EMAIL_NOT_VERIFIED
			    .getId())) {
		this.message = "Учетная запись не активирована.";
	    } else {
		if (hasher.verify(tempUser.getHashedPassword(),
			this.password)) {
		    isLogin = true;
		    this.user = tempUser;
		    this.login = StringUtils.EMPTY;
		    this.password = StringUtils.EMPTY;
		    this.tempUser = null;
		    PrimeFaces.current().dialog().closeDynamic(null);
		    System.out.println("Valid");
		    return;
		} else {
		    this.message = "Неверный пароль.";
		    System.out.println("Not Valid");
		}
	    }
	} else {
	    this.message = "Учетная запись не существует.";
	    System.out.println("Null");
	}

    }

    public void logout() {
	HttpSession session = (HttpSession) FacesContext
		.getCurrentInstance().getExternalContext()
		.getSession(false);
	session.invalidate();
    }

    public void wipe() {
	this.user = null;
	this.login = StringUtils.EMPTY;
	this.password = StringUtils.EMPTY;
	this.message = StringUtils.EMPTY;
    }

    /**
     * @return the login
     */
    public String getLogin() {
	return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
	this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
	return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @return the user
     */
    public UserAccount getUser() {
	return user;
    }

    /**
     * @return the isLogin
     */
    public boolean getLoginStatus() {
	return isLogin;
    }

    /**
     * @return the message
     */
    public String getMessage() {
	return message;
    }

}
