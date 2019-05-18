package beans;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

import model.Blacklist;
import model.Email;
import model.Friendlist;
import model.UserAccount;
import services.BlacklistService;
import services.EmailService;
import services.FriendlistService;
import services.UpdateHistoryService;
import services.UserAccountService;
import services.UserStatusService;

@Named
@ViewScoped
public class UserAccountBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5999259509194624596L;

    private final String MANLOGO = "/resources/images/man-no.png";
    private final String WOMANLOGO = "/resources/images/woman-no.png";
    private final String NOLOGO = "/resources/images/nologo.png";
    private final String LOGOBASE = "/resources/images/Users/";

    @Inject
    private UserAccountService userAccountService;
    @Inject
    private EmailService emailService;
    @Inject
    private BlacklistService blacklistService;
    @Inject
    private FriendlistService friendlistService;
    @Inject
    private UpdateHistoryService history;
    @Inject
    private SessionBean session;
 
    private UserAccount user;
    private Email selectedEmail;
    private Email newEmail;
    private String destLogin;
    private UploadedFile file;
    private String logoURL;
    private String updateUserHash;
    private String updateUserListsHash;
    
    @PostConstruct
    public void init() {
	HttpServletRequest request = (HttpServletRequest) FacesContext
		.getCurrentInstance().getExternalContext()
		.getRequest();
	if (request.getHeader("X-Requested-With") == null) {
	    this.setUser(this.userAccountService.getById(
		    Integer.parseInt(request.getParameter("id"))));
	}
	this.updateUserHash = UpdateHistoryService.getStarthash();
	this.updateUserListsHash = UpdateHistoryService.getStarthash();
	this.newEmail = new Email();
	String possibleURL = this.LOGOBASE
		+ this.user.getId().toString() + '/'
		+ (this.user.getPicture() != null
			? this.user.getPicture()
			: "null");
	String path = Faces.getRealPath("/") + possibleURL;
	File f = new File(path);
	System.out.println(path);
	if (f.exists() && !f.isDirectory()) {
	    System.out.println("+");
	    this.logoURL = possibleURL;
	} else {
	    switch (this.user.getGender()) {
	    case "M":
		this.logoURL = this.MANLOGO;
		break;
	    case "F":
		this.logoURL = this.WOMANLOGO;
		break;
	    default:
		this.logoURL = this.NOLOGO;
		break;
	    }
	}
    }

    public void updateHandle() {
	userAccountService.flushChanges(this.user);
	FacesContext context = FacesContext.getCurrentInstance();
	context.addMessage(null, new FacesMessage("Уведомление",
		"Изменения сохранены"));
    }

    public void handleFileUpload(FileUploadEvent event)
	    throws Exception {
	FacesContext context = FacesContext.getCurrentInstance();
	this.file = event.getFile();
	try {
	    String fileName = this.file.getFileName();
	    fileName = FilenameUtils.getBaseName(fileName) + "."
		    + FilenameUtils.getExtension(fileName);
	    String path = Faces.getRealPath("/")
		    + "/resources/images/Users/"
		    + this.user.getId().toString();
	    File parent = new File(path);
	    parent.mkdirs();
	    File f = new File(parent, fileName);
	    FileUtils.writeByteArrayToFile(f,
		    this.file.getContents());
	    this.user.setPicture(fileName);
	    this.logoURL = this.LOGOBASE
		    + this.user.getId().toString() + "/" + fileName;
	    this.userAccountService.flushChanges(this.user);
	    context.addMessage(null, new FacesMessage("Уведомление",
		    "Изменения сохранены"));
	} catch (Exception e) {
	    context.addMessage(null, new FacesMessage("Уведомление",
		    "Ошибка при изменении! Попробуйте еще раз позже!"));
	}
    }

    public void sendEmail() {
	FacesContext context = FacesContext.getCurrentInstance();
	UserAccount dest = this.userAccountService
		.getByLogin(destLogin);
	this.destLogin = "";
	if (null == dest) {
	    context.addMessage(null, new FacesMessage("Уведомление",
		    "Ошибка при отправке! Пользователя не существует!"));
	    return;
	}

	if(dest.isInBlacklist(this.user.getId())) {
	    context.addMessage(null, new FacesMessage("Уведомление",
		    "Вы не можете отправить письмо этому пользователю!"));
	    return;
	}
	
	this.newEmail.setUserAccount1(this.user);
	this.newEmail.setUserAccount2(dest);
	this.newEmail = this.emailService.create(this.newEmail);
	if (null == this.newEmail) {
	    context.addMessage(null, new FacesMessage("Уведомление",
		    "Ошибка при отправке! Попробуйте еще раз позже!"));
	    return;
	}
	this.newEmail = null;

	this.history.setUFEHash(dest.getId().intValue(),
		DigestUtils.md5Hex((new Date()).toString()));

	this.user = this.userAccountService
		.getById(this.user.getId());
	context.addMessage(null, new FacesMessage("Уведомление",
		"Письмо отправлено!"));
	return;
    }

    public void update() {
	String UFEHash = history
		.getUFEHash(this.user.getId().intValue());
	if (!UFEHash.equals(this.updateUserHash)) {
	    this.user = this.userAccountService
		    .getById(this.user.getId());
	    this.updateUserHash = UFEHash;
	}
    }
    public void updateLists() {
	String UFLHash = history
		.getUFLHash(this.user.getId().intValue());
	if (!UFLHash.equals(this.updateUserListsHash)) {
	    this.user = this.userAccountService
		    .getById(this.user.getId());
	    this.updateUserListsHash = UFLHash;
	}
    }

    public void setFriendlist() {
	Friendlist newFriend = new Friendlist();
	newFriend.setUserAccount2(this.session.getUser());
	newFriend.setUserAccount1(this.user);
	this.friendlistService.create(newFriend);
	this.user = this.userAccountService.getById(this.user.getId());
	this.session.refresh();
	this.history.setUFLHash(this.session.getUser().getId().intValue(),
		DigestUtils.md5Hex((new Date()).toString()));
    }
    
    public void setBlacklist() {
	Blacklist newBlacklist = new Blacklist();
	newBlacklist.setUserAccount2(this.session.getUser());
	newBlacklist.setUserAccount1(this.user);
	this.blacklistService.create(newBlacklist);
	this.user = this.userAccountService.getById(this.user.getId());
	this.session.refresh();
	this.history.setUFLHash(this.session.getUser().getId().intValue(),
		DigestUtils.md5Hex((new Date()).toString()));
    }
    
    public List<String> friendComplete(String query){
	return this.user.getFriendlists2().stream().map(x->x.getUserAccount1().getUsername()).filter(x->x.startsWith(query)).collect(Collectors.toList());
    }
    
    public void changeUserStatus(boolean block) {
	int status = block ? UserStatusService.Statuses.BLOCKED.getId() : UserStatusService.Statuses.VERIFIED.getId();
	this.userAccountService.changeStatus(this.user, status);
	this.user = this.userAccountService.getById(this.user.getId());
	this.history.setUFLHash(this.session.getUser().getId().intValue(),
		DigestUtils.md5Hex((new Date()).toString()));
    }
    
    public void blockUser() {
	this.userAccountService.changeStatus(this.user, UserStatusService.Statuses.BLOCKED.getId());
	this.user = this.userAccountService.getById(this.user.getId());
	this.history.setUFLHash(this.session.getUser().getId().intValue(),
		DigestUtils.md5Hex((new Date()).toString()));
    }
    
    public void unblockUser() {
	this.userAccountService.changeStatus(this.user, UserStatusService.Statuses.VERIFIED.getId());
	this.user = this.userAccountService.getById(this.user.getId());
	this.history.setUFLHash(this.session.getUser().getId().intValue(),
		DigestUtils.md5Hex((new Date()).toString()));
    }
    
    /**
     * @return the user
     */
    public UserAccount getUser() {
	return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserAccount user) {
	this.user = user;
    }

    /**
     * @return the logoURL
     */
    public String getLogoURL() {
	return logoURL;
    }

    /**
     * @param logoURL the logoURL to set
     */
    public void setLogoURL(String logoURL) {
	this.logoURL = logoURL;
    }

    /**
     * @return the selectedEmail
     */
    public Email getSelectedEmail() {
	return selectedEmail;
    }

    /**
     * @param selectedEmail the selectedEmail to set
     */
    public void setSelectedEmail(Email selectedEmail) {
	this.selectedEmail = selectedEmail;
    }

    /**
     * @return the newEmail
     */
    public Email getNewEmail() {
	return newEmail;
    }

    /**
     * @param newEmail the newEmail to set
     */
    public void setNewEmail(Email newEmail) {
	this.newEmail = newEmail;
    }

    /**
     * @return the destLogin
     */
    public String getDestLogin() {
	return destLogin;
    }

    /**
     * @param destLogin the destLogin to set
     */
    public void setDestLogin(String destLogin) {
	this.destLogin = destLogin;
    }

}
