package beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.PrimeFaces;

import model.Post;
import model.Thread;
import services.ThreadService;

@Named
@ViewScoped
public class ThreadBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5340224729515981780L;
    
    private Thread currentThread;
    private List<Post> approvedPosts;
    @Inject
    private ThreadService thread_service;
    private String postText;
    //https://www.youtube.com/watch?v=oD9dU_hGjLc
    @PostConstruct
    public void init() {
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	/*if(null == request.getParameter("id")) {
	    return;
	}*/
	currentThread = thread_service.getById(Integer.parseInt(request.getParameter("id")));
	approvedPosts = currentThread.getApprovedPosts();
    }
    
    public void showMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Test", this.postText);
         
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }
    
    /**
     * @return the currentThread
     */
    public Thread getCurrentThread() {
        return currentThread;
    }
    /**
     * @param currentThread the currentThread to set
     */
    public void setCurrentThread(Thread currentThread) {
        this.currentThread = currentThread;
    }
    /**
     * @return the approvedPosts
     */
    public List<Post> getApprovedPosts() {
        return approvedPosts;
    }
    /**
     * @param approvedPosts the approvedPosts to set
     */
    public void setApprovedPosts(List<Post> approvedPosts) {
        this.approvedPosts = approvedPosts;
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
