package beans;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import helpers.AppSettings;
import model.UserAccount;
import services.UserAccountService;
import validators.UniqueEmail;
import validators.UniqueUsername;

@Named
@ViewScoped
public class SignupBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1916799682231854683L;

    private final String ACCOUNT_STEP = "account";
    private final String PERSONAL_STEP = "personal";
    private final String AVATAR_STEP = "avatar";
    private final String CONFIRM_STEP = "confirm";

    @Inject
    private UserAccountService userService;

    private Argon2 hasher;
    private UserAccount user;

    @NotNull
    @Size(max = 99, message = "Длина поля 'Пароль' не должна превышать 99")
    private String password;
    @Size(max = 2000, message = "Длина поля 'О себе' не должна превышать 2000")
    private String about;
    @Size(max = 500, message = "Длина поля 'Адрес' не должна превышать 500")
    private String adress;
    private Date birthday;
    @NotNull
    @Email
    @UniqueEmail
    @Size(max = 255, message = "Длина поля 'Электронная почта' не должна превышать 255")
    private String email;
    @Size(max = 100, message = "Длина поля 'Имя' не должна превышать 100")
    private String firstName;
    @Size(max = 1)
    private String gender;
    @Size(max = 100, message = "Длина поля 'Фамилия' не должна превышать 100")
    private String lastName;
    @Size(max = 100, message = "Длина поля 'Отчество' не должна превышать 100")
    private String middleName;
    @Size(max = 100, message = "Длина поля 'Статус' не должна превышать 100")
    private String status;
    @NotNull
    @UniqueUsername
    @Size(min = 3, max = 99, message = "Длина поля 'Имя пользователя' должна быть в пределах от 3 до 99")
    private String username;
    @Size(max = 255)
    private String verifyCode;

    private boolean isCreated = false;
    private UploadedFile file;
    private StreamedContent content;
    private String imageURL;
    private Date minDate;
    private Date maxDate;

    @PostConstruct
    public void init() {
	Date today = new Date();
	user = new UserAccount();
	hasher = Argon2Factory.create(Argon2Types.ARGON2id);
	minDate = new Date(
		today.getTime() - (100 * 365 * 24 * 60 * 60 * 1000));
	maxDate = today;
    }

    public String onFlowProcess(FlowEvent event) {
	UserAccount newUser = null;
	String currentStep = event.getOldStep();
	if (currentStep.equals(this.ACCOUNT_STEP) && !isCreated) {

	    this.user.setUsername(this.username);
	    this.user.setEmail(this.email);
	    this.user.setHashedPassword(hasher.hash(
		    AppSettings.HASH_ROUNDS, AppSettings.HASH_MEMORY,
		    AppSettings.HASH_THREADS, this.password));
	    this.user.setVerifyCode(DigestUtils
		    .md5Hex(new Date().toString() + "HEX"));
	    newUser = this.userService.create(this.user);
	    if (null == newUser)
		return currentStep;
	    this.user = newUser;
	    this.isCreated = true;
	}
	return event.getNewStep();
    }

    public void read() throws IOException {

    }

    public void handleFileUpload(FileUploadEvent event)
	    throws Exception {
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
	    this.imageURL = Faces.getRequestBaseURL()
		    + "resources/images/Users/"
		    + this.user.getId().toString() + '/' + fileName;
	    this.user.setPicture(fileName);
	} catch (Exception e) {
	}
    }

    public void save() {
	this.user.setFirstName(this.firstName);
	this.user.setMiddleName(this.middleName);
	this.user.setLastName(this.lastName);
	this.user.setGender(this.gender);
	this.user.setAbout(this.about);
	this.user.setAdress(this.adress);
	this.user.setBirthday(this.birthday!=null?new Timestamp(this.birthday.getTime()):null);
	userService.flushChanges(this.user);
	sendMail();

	try {
	    FacesContext.getCurrentInstance().getExternalContext()
		    .redirect("home.xhtml");
	} catch (IOException e) {

	}
    }

    public void sendMail() {
	HtmlEmail email = new HtmlEmail();
	email.setHostName(AppSettings.EMAIL_SERVER);
	email.setSmtpPort(AppSettings.EMAIL_PORT);
	email.setAuthenticator(new DefaultAuthenticator(
		AppSettings.EMAIL_LOGIN, AppSettings.EMAIL_PASSWORD));
	email.setSSLOnConnect(true);
	try {
	    email.setFrom(AppSettings.EMAIL_LOGIN + "@yandex.com");
	    email.setSubject(AppSettings.EMAIL_THEME);
	    email.setHtmlMsg(AppSettings.createEmailHTML(
		    this.user.getId().toString(),
		    this.user.getVerifyCode()));
	    email.addTo(this.user.getEmail());
	    email.setTextMsg(AppSettings.createEmailPlain(
		    this.user.getId().toString(),
		    this.user.getVerifyCode()));
	    email.send();
	} catch (EmailException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public void setBirthdayFromDate(Date date) {
	this.birthday = null == this.birthday ? null
		: new Timestamp(date.getTime());
    }

    public Date getBirthdayFromDate() {
	return null == this.birthday ? null
		: new Date(this.birthday.getTime());
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

    /**
     * @return the minDate
     */
    public Date getMinDate() {
	return minDate;
    }

    /**
     * @return the maxDate
     */
    public Date getMaxDate() {
	return maxDate;
    }

    /**
     * @return the aCCOUNT_STEP
     */
    public String getACCOUNT_STEP() {
	return ACCOUNT_STEP;
    }

    /**
     * @return the pERSONAL_STEP
     */
    public String getPERSONAL_STEP() {
	return PERSONAL_STEP;
    }

    /**
     * @return the aVATAR_STEP
     */
    public String getAVATAR_STEP() {
	return AVATAR_STEP;
    }

    /**
     * @return the cONFIRM_STEP
     */
    public String getCONFIRM_STEP() {
	return CONFIRM_STEP;
    }

    /**
     * @return the about
     */
    public String getAbout() {
	return about;
    }

    /**
     * @param about the about to set
     */
    public void setAbout(String about) {
	this.about = about;
    }

    /**
     * @return the adress
     */
    public String getAdress() {
	return adress;
    }

    /**
     * @param adress the adress to set
     */
    public void setAdress(String adress) {
	this.adress = adress;
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
	return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
	this.birthday = birthday;
    }

    /**
     * @return the email
     */
    public String getEmail() {
	return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
	return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    /**
     * @return the gender
     */
    public String getGender() {
	return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
	this.gender = gender;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
	return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
	return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
	this.middleName = middleName;
    }

    /**
     * @return the status
     */
    public String getStatus() {
	return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
	this.status = status;
    }

    /**
     * @return the username
     */
    public String getUsername() {
	return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * @return the file
     */
    public UploadedFile getFile() {
	return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(UploadedFile file) {
	this.file = file;
    }

    /**
     * @return the content
     */
    public StreamedContent getContent() {
	return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(StreamedContent content) {
	this.content = content;
    }

    /**
     * @return the imageURL
     */
    public String getImageURL() {
	return imageURL;
    }

    /**
     * @param imageURL the imageURL to set
     */
    public void setImageURL(String imageURL) {
	this.imageURL = imageURL;
    }

}
