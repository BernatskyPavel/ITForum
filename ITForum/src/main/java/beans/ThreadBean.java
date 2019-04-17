package beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.primefaces.PrimeFaces;

import model.Post;
import model.Thread;
import services.PostService;
import services.ThreadService;
import services.UpdateHistoryService;

@Named
@ViewScoped
public class ThreadBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5340224729515981780L;
    @Inject
    private SessionBean session;
    @Inject
    private ThreadService threadService;
    @Inject
    private PostService postService;
    @Inject 
    private HomeBean homeBean;
    @Inject 
    private UpdateHistoryService history;
    private Thread currentThread;
    private List<Post> approvedPosts;
    @NotNull(message = "Сообщение не может быть пустым!")
    @Size(max = 20000, message = "Превышен максимальный размер!")
    private String postText;
    //https://www.youtube.com/watch?v=oD9dU_hGjLc
    
    private String lastUpdateHash;
    @PostConstruct
    public void init() {
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	/*if(null == request.getParameter("id")) {
	    return;
	}*/
	currentThread = threadService.getById(Integer.parseInt(request.getParameter("id")));
	approvedPosts = currentThread.getApprovedPosts();
	lastUpdateHash = UpdateHistoryService.getStarthash();
    }
    
    public void createPost() {
	Post newPost = new Post();
	newPost.setContents(this.postText);
	newPost.setThread(this.currentThread);
	newPost.setUserAccount(session.getUser());
	postService.create(newPost);
	homeBean.refresh();
	approvedPosts = currentThread.getApprovedPosts();
	this.postText = StringUtils.EMPTY;
	this.lastUpdateHash = DigestUtils.md5Hex((new Date()).toString());
    }
    
    public void update() {
	if(!this.lastUpdateHash.equals(history.getLastGlobalThreadUpdateHash())) {
	    this.lastUpdateHash = DigestUtils.md5Hex((new Date()).toString());
	    history.setLastGlobalThreadUpdateHash(this.lastUpdateHash);
	    approvedPosts = currentThread.getApprovedPosts();
	}
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
