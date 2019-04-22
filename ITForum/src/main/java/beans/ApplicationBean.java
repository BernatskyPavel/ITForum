package beans;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ApplicationBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5597071891251145965L;
    
    public void refresh(String view) {
	switch (view) {
	case "/user.xhtml":
	    FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove("userAccountBean");
	    break;

	default:
	    break;
	}
    }

}
