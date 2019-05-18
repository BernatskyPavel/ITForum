package beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.model.menu.MenuModel;

import model.Category;
import model.Thread;
import services.ApplicationService;
import services.CategoryService;
import services.StatusService;
import services.ThreadService;
import services.UpdateHistoryService;

@Named
@ViewScoped
public class CategoryBean implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1346475898933067165L;
    private Category currentCategory;
    private List<Category> approvedSubCategories;
    @Inject
    private ApplicationService applicationService;
    @Inject
    private CategoryService categoryService;
    @Inject
    private SessionBean session;
    @Inject
    private ThreadService threadService;
    @Inject
    private UpdateHistoryService history;
    private Thread selectedThread;
    private String updateThreadHash;
    @NotNull
    @Size(max = 255, message = "Превышен максимальный размер!")
    private String newPostHeader;
    @NotNull
    @Size(max = 15000, message = "Превышен максимальный размер!")
    private String newPostContent;
    
    private MenuModel breadcrumbs;

    @PostConstruct
    public void init() {
	HttpServletRequest request = (HttpServletRequest) FacesContext
		.getCurrentInstance().getExternalContext()
		.getRequest();
	if (request.getHeader("X-Requested-With") == null) {
	    this.currentCategory = this.categoryService.getById(
		    Integer.parseInt(request.getParameter("id")));
	    this.approvedSubCategories = this.currentCategory
		    .getApprovedSubCategories();
	}
	this.updateThreadHash = UpdateHistoryService.getStarthash();
	breadcrumbs = applicationService.getBreadcrumbPath(this.currentCategory);
    }

    public void refresh() {
	String CFTHash = history
		.getCFTHash(this.currentCategory.getId().intValue());
	if (!CFTHash.equals(this.updateThreadHash)) {
	    this.currentCategory = this.categoryService
		    .getById(this.currentCategory.getId());
	    this.approvedSubCategories = this.currentCategory
		    .getApprovedSubCategories();
	    this.updateThreadHash = CFTHash;
	}
    }

    public void createThread() {
	if (!this.session.getUser().isBlocked()) {
	    model.Thread thread = new model.Thread();
	    thread.setSubject(this.applicationService.censoredText(this.newPostHeader));
	    thread.setContent(this.applicationService.censoredText(this.newPostContent));
	    thread.setCategoryBean(this.currentCategory);
	    thread.setUserAccount(session.getUser());
	    this.threadService.create(thread);
	    this.currentCategory = this.categoryService
		    .getById(this.currentCategory.getId());
	    this.approvedSubCategories = this.currentCategory
		    .getApprovedSubCategories();
	    this.updateThreadHash = DigestUtils
		    .md5Hex((new Date()).toString());
	    this.history.setCFTHash(
		    this.currentCategory.getId().intValue(),
		    this.updateThreadHash);
	}
    }

    public void completeThread() {
	if (null != this.selectedThread) {
	    this.threadService.changeStatus(selectedThread,
		    StatusService.Statuses.COMPLETED.getId());
	    this.update();
	}
    }

    public void blockThread() {
	if (null != this.selectedThread) {
	    this.threadService.changeStatus(selectedThread,
		    StatusService.Statuses.BLOCKED.getId());
	    this.update();
	}
    }

    public void unblockThread() {
	if (null != this.selectedThread) {
	    this.threadService.changeStatus(selectedThread,
		    StatusService.Statuses.APPROVED.getId());
	    this.update();
	}
    }

    private void update() {
	this.currentCategory = this.categoryService
		.getById(this.currentCategory.getId());
	this.approvedSubCategories = this.currentCategory
		.getApprovedSubCategories();
	this.updateThreadHash = DigestUtils
		.md5Hex((new Date()).toString());
	this.history.setCFTHash(
		this.currentCategory.getId().intValue(),
		this.updateThreadHash);
    }

    /**
     * @return the current_category
     */
    public Category getCurrentCategory() {
	return currentCategory;
    }

    /**
     * @param current_category the current_category to set
     */
    public void setCurrentCategory(Category currentCategory) {
	this.currentCategory = currentCategory;
    }

    /**
     * @return the approvedSubCategories
     */
    public List<Category> getApprovedSubCategories() {
	return approvedSubCategories;
    }

    /**
     * @return the newPostHeader
     */
    public String getNewPostHeader() {
	return newPostHeader;
    }

    /**
     * @param newPostHeader the newPostHeader to set
     */
    public void setNewPostHeader(String newPostHeader) {
	this.newPostHeader = newPostHeader;
    }

    /**
     * @return the newPostContent
     */
    public String getNewPostContent() {
	return newPostContent;
    }

    /**
     * @param newPostContent the newPostContent to set
     */
    public void setNewPostContent(String newPostContent) {
	this.newPostContent = newPostContent;
    }

    /**
     * @return the selectedThread
     */
    public Thread getSelectedThread() {
	return selectedThread;
    }

    /**
     * @param selectedThread the selectedThread to set
     */
    public void setSelectedThread(Thread selectedThread) {
	this.selectedThread = selectedThread;
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
