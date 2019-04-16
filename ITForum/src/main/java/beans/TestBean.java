package beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class TestBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 2842688346905334729L;
    private String postText;
    
    public void showMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Test", this.postText);
         
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }
    
    /**
     * @return the postText
     */
    public String getPostText() {
        return postText;
    }

    /**
     * @param postText the postText to set
     */
    public void setPostText(String postText) {
        this.postText = postText;
    }
}
