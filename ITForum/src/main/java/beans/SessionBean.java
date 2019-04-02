package beans;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class SessionBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -124466934030612288L;
    
    private boolean isAdmin;
    private boolean isModerator;
    private int     userId;
    
    /**
     * @return the isAdmin
     */
    public boolean isAdmin() {
        return isAdmin;
    }
    /**
     * @param isAdmin the isAdmin to set
     */
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    /**
     * @return the isModerator
     */
    public boolean isModerator() {
        return isModerator;
    }
    /**
     * @param isModerator the isModerator to set
     */
    public void setModerator(boolean isModerator) {
        this.isModerator = isModerator;
    }
    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
