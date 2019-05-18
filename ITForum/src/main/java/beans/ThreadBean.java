package beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
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
import org.primefaces.model.menu.MenuModel;

import model.Post;
import model.Thread;
import services.ApplicationService;
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
    private ApplicationService applicationService;
    @Inject
    private SessionBean session;
    @Inject
    private ThreadService threadService;
    @Inject
    private PostService postService;
    @Inject
    private UpdateHistoryService history;
    private Thread currentThread;
    private List<Post> approvedPosts;
    @NotNull(message = "Сообщение не может быть пустым!")
    @Size(max = 20000, message = "Превышен максимальный размер!")
    private String postText;
    // https://www.youtube.com/watch?v=oD9dU_hGjLc

    private String lastUpdateHash;

    private MenuModel breadcrumbs;
    
    @PostConstruct
    public void init() {
	HttpServletRequest request = (HttpServletRequest) FacesContext
		.getCurrentInstance().getExternalContext()
		.getRequest();
	/*
	 * if(null == request.getParameter("id")) { return; }
	 */
	this.currentThread = this.threadService.getById(
		Integer.parseInt(request.getParameter("id")));
	this.approvedPosts = this.currentThread.getApprovedPosts();
	this.lastUpdateHash = UpdateHistoryService.getStarthash();
	this.setBreadcrumbs(applicationService.getBreadcrumbPath(this.currentThread));
    }

    public void createPost() {
	if (!this.currentThread.isCompleted()
		&& !this.currentThread.isBlocked()
		&& !this.session.getUser().isBlocked()) {
	    Post newPost = new Post();
	    newPost.setContents(this.applicationService.censoredText(this.postText));
	    newPost.setThread(this.currentThread);
	    newPost.setUserAccount(session.getUser());
	    this.postService.create(newPost);
	    this.currentThread = this.threadService
		    .getById(this.currentThread.getId());
	    this.approvedPosts = this.currentThread
		    .getApprovedPosts();
	    this.postText = StringUtils.EMPTY;
	    this.lastUpdateHash = DigestUtils
		    .md5Hex((new Date()).toString());
	    this.history.setCFTHash(
		    this.currentThread.getCategoryBean().getId()
			    .intValue(),
		    DigestUtils.md5Hex((new Date()).toString()));
	} else {
	    try {
		FacesContext.getCurrentInstance().getExternalContext()
			.redirect("/category.xhtml?id="
				+ this.currentThread.getCategoryBean()
					.getId().intValue());
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public void update() {
	if (!this.lastUpdateHash
		.equals(history.getLastGlobalThreadUpdateHash())) {
	    this.lastUpdateHash = DigestUtils
		    .md5Hex((new Date()).toString());
	    history.setLastGlobalThreadUpdateHash(
		    this.lastUpdateHash);
	    this.currentThread = this.threadService
		    .getById(this.currentThread.getId());
	    this.approvedPosts = this.currentThread
		    .getApprovedPosts();
	}
    }

    public void showMessage() {
	FacesMessage message = new FacesMessage(
		FacesMessage.SEVERITY_INFO, "Test", this.postText);

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

    /**
     * @return the breadcrumbs
     */
    public MenuModel getBreadcrumbs() {
	return breadcrumbs;
    }

    /**
     * @param breadcrumbs the breadcrumbs to set
     */
    public void setBreadcrumbs(MenuModel breadcrumbs) {
	this.breadcrumbs = breadcrumbs;
    }
}
