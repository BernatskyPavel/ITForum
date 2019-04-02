package beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;


@Named
@ViewScoped
public class LoginBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6955508471291131930L;
    
    private Argon2 hasher;
    
    private String login;
    private String password;
    private String dbPassword;
    
    @PostConstruct
    public void init() {
	hasher = Argon2Factory.create(Argon2Types.ARGON2id);
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

    
    

}
