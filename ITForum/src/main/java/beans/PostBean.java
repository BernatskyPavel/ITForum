package beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

@Named
@RequestScoped
public class PostBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8889238095929684164L;
    
    private String postText;
    
    public String getPostText() {
	return postText;
    }

    /**
     * @param postText the postText to set
     */
    public void setPostText(String postText) {
        this.postText = postText;
    }
    
    public void showMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Test", this.postText);
         
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }
}