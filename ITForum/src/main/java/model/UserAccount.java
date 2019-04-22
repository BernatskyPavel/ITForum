package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import services.UserStatusService;

/**
 * The persistent class for the user_accounts database table.
 * 
 */
@Entity
@Table(name = "user_accounts")
@NamedQueries({
	@NamedQuery(name = "UserAccount.findAll", query = "SELECT u FROM UserAccount u"),
	@NamedQuery(name = "UserAccount.findById", query = "SELECT u FROM UserAccount u WHERE u.id = :id"),
	@NamedQuery(name = "UserAccount.findByUsername", query = "SELECT u FROM UserAccount u WHERE u.username = :username"),
	@NamedQuery(name = "UserAccount.findByEmail", query = "SELECT u FROM UserAccount u WHERE u.email = :email"),
	@NamedQuery(name = "UserAccount.countByUsername", query = "SELECT COUNT(u) FROM UserAccount u WHERE u.username = :username"),
	@NamedQuery(name = "UserAccount.countByEmail", query = "SELECT COUNT(u) FROM UserAccount u WHERE u.email = :email") })

public class UserAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String about;
    private String adress;
    private Timestamp birthday;
    private Timestamp created;
    private String email;
    private String firstName;
    private String gender;
    private String hashedPassword;
    private Boolean isAdmin = false;
    private Boolean isModerator = false;
    private Timestamp lastActivity;
    private String lastName;
    private String middleName;
    private String picture;
    private String status;
    private String username;
    private String verifyCode;
    private List<Category> categories;
    private List<Email> emails1;
    private List<Email> emails2;
    private List<Post> posts;
    private List<Section> sections;
    private List<Thread> threads;
    private UserStatus userStatusBean;
    private List<Blacklist> blacklists1;
    private List<Blacklist> blacklists2;
    private List<Friendlist> friendlists1;
    private List<Friendlist> friendlists2;

    public UserAccount() {
    }
    
    @Transient
    public String getGenderString() {
	String result = "Не указан";
	if (this.gender != null)
	    switch (this.gender) {
	    case "M":
		result = "Мужской";
		break;
	    case "F":
		result = "Женксий";
		break;
	    case "O":
		result = "Другой";
		break;
	    default:
		result = "Не указан";
		break;
	    }
	return result;
    }

    @Transient
    public Date getBirthdayAsDate() {
	return this.birthday != null ? new Date(this.birthday.getTime()) : null;
    }
    @Transient
    public boolean isInFriendlist(Integer id) {
	return this.friendlists2.stream().anyMatch(x->x.getUserAccount1().getId().intValue() == id.intValue());
    }
    @Transient
    public boolean isInBlacklist(Integer id) {
	return this.blacklists2.stream().anyMatch(x->x.getUserAccount1().getId().intValue() == id.intValue());
    }
    @Transient
    public void setBirthdayAsDate(Date date) {
	this.birthday = new Timestamp(date.getTime());
    }

    @Transient
    public boolean isBlocked() {
	return this.userStatusBean.getId().intValue() == UserStatusService.Statuses.BLOCKED.getId();
    }
    
    @Id
    @GeneratedValue(generator = "my_seq")
    @SequenceGenerator(name = "my_seq", sequenceName = "user_accounts_id_seq", allocationSize = 1)
    public Integer getId() {
	return this.id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getAbout() {
	return this.about;
    }

    public void setAbout(String about) {
	this.about = about;
    }

    public String getAdress() {
	return this.adress;
    }

    public void setAdress(String adress) {
	this.adress = adress;
    }

    public Timestamp getBirthday() {
	return this.birthday;
    }

    public void setBirthday(Timestamp birthday) {
	this.birthday = birthday;
    }

    @Column(insertable = false)
    public Timestamp getCreated() {
	return this.created;
    }

    public void setCreated(Timestamp created) {
	this.created = created;
    }

    @Column(unique = true)
    public String getEmail() {
	return this.email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    @Column(name = "first_name")
    public String getFirstName() {
	return this.firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getGender() {
	return this.gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

    @Column(name = "hashed_password")
    public String getHashedPassword() {
	return this.hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
	this.hashedPassword = hashedPassword;
    }

    @Column(name = "is_admin")
    public Boolean getIsAdmin() {
	return this.isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
	this.isAdmin = isAdmin;
    }

    @Column(name = "is_moderator")
    public Boolean getIsModerator() {
	return this.isModerator;
    }

    public void setIsModerator(Boolean isModerator) {
	this.isModerator = isModerator;
    }

    @Column(name = "last_activity", insertable = false)
    public Timestamp getLastActivity() {
	return this.lastActivity;
    }

    public void setLastActivity(Timestamp lastActivity) {
	this.lastActivity = lastActivity;
    }

    @Column(name = "last_name")
    public String getLastName() {
	return this.lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    @Column(name = "middle_name")
    public String getMiddleName() {
	return this.middleName;
    }

    public void setMiddleName(String middleName) {
	this.middleName = middleName;
    }

    public String getPicture() {
	return this.picture;
    }

    public void setPicture(String picture) {
	this.picture = picture;
    }

    public String getStatus() {
	return this.status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    @Column(unique = true)
    public String getUsername() {
	return this.username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    @Column(name = "verify_code")
    public String getVerifyCode() {
	return this.verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
	this.verifyCode = verifyCode;
    }

    // bi-directional many-to-one association to Category
    @OneToMany(mappedBy = "userAccount")
    public List<Category> getCategories() {
	return this.categories;
    }

    public void setCategories(List<Category> categories) {
	this.categories = categories;
    }

    public Category addCategory(Category category) {
	getCategories().add(category);
	category.setUserAccount(this);

	return category;
    }

    public Category removeCategory(Category category) {
	getCategories().remove(category);
	category.setUserAccount(null);

	return category;
    }

    // bi-directional many-to-one association to Email
    @OneToMany(mappedBy = "userAccount1")
    public List<Email> getEmails1() {
	return this.emails1;
    }

    public void setEmails1(List<Email> emails1) {
	this.emails1 = emails1;
    }

    public Email addEmails1(Email emails1) {
	getEmails1().add(emails1);
	emails1.setUserAccount1(this);

	return emails1;
    }

    public Email removeEmails1(Email emails1) {
	getEmails1().remove(emails1);
	emails1.setUserAccount1(null);

	return emails1;
    }

    // bi-directional many-to-one association to Email
    @OneToMany(mappedBy = "userAccount2")
    public List<Email> getEmails2() {
	return this.emails2;
    }

    public void setEmails2(List<Email> emails2) {
	this.emails2 = emails2;
    }

    public Email addEmails2(Email emails2) {
	getEmails2().add(emails2);
	emails2.setUserAccount2(this);

	return emails2;
    }

    public Email removeEmails2(Email emails2) {
	getEmails2().remove(emails2);
	emails2.setUserAccount2(null);

	return emails2;
    }

    // bi-directional many-to-one association to Post
    @OneToMany(mappedBy = "userAccount")
    public List<Post> getPosts() {
	return this.posts;
    }

    public void setPosts(List<Post> posts) {
	this.posts = posts;
    }

    public Post addPost(Post post) {
	getPosts().add(post);
	post.setUserAccount(this);

	return post;
    }

    public Post removePost(Post post) {
	getPosts().remove(post);
	post.setUserAccount(null);

	return post;
    }

    // bi-directional many-to-one association to Section
    @OneToMany(mappedBy = "userAccount")
    public List<Section> getSections() {
	return this.sections;
    }

    public void setSections(List<Section> sections) {
	this.sections = sections;
    }

    public Section addSection(Section section) {
	getSections().add(section);
	section.setUserAccount(this);

	return section;
    }

    public Section removeSection(Section section) {
	getSections().remove(section);
	section.setUserAccount(null);

	return section;
    }

    // bi-directional many-to-one association to Thread
    @OneToMany(mappedBy = "userAccount")
    public List<Thread> getThreads() {
	return this.threads;
    }

    public void setThreads(List<Thread> threads) {
	this.threads = threads;
    }

    public Thread addThread(Thread thread) {
	getThreads().add(thread);
	thread.setUserAccount(this);

	return thread;
    }

    public Thread removeThread(Thread thread) {
	getThreads().remove(thread);
	thread.setUserAccount(null);

	return thread;
    }

    // bi-directional many-to-one association to UserStatus
    @ManyToOne
    @JoinColumn(name = "user_status")
    public UserStatus getUserStatusBean() {
	return this.userStatusBean;
    }

    public void setUserStatusBean(UserStatus userStatusBean) {
	this.userStatusBean = userStatusBean;
    }

    // bi-directional many-to-one association to Blacklist
    @OneToMany(mappedBy = "userAccount1")
    public List<Blacklist> getBlacklists1() {
	return this.blacklists1;
    }

    public void setBlacklists1(List<Blacklist> blacklists1) {
	this.blacklists1 = blacklists1;
    }

    public Blacklist addBlacklists1(Blacklist blacklists1) {
	getBlacklists1().add(blacklists1);
	blacklists1.setUserAccount1(this);

	return blacklists1;
    }

    public Blacklist removeBlacklists1(Blacklist blacklists1) {
	getBlacklists1().remove(blacklists1);
	blacklists1.setUserAccount1(null);

	return blacklists1;
    }

    // bi-directional many-to-one association to Blacklist
    @OneToMany(mappedBy = "userAccount2")
    public List<Blacklist> getBlacklists2() {
	return this.blacklists2;
    }

    public void setBlacklists2(List<Blacklist> blacklists2) {
	this.blacklists2 = blacklists2;
    }

    public Blacklist addBlacklists2(Blacklist blacklists2) {
	getBlacklists2().add(blacklists2);
	blacklists2.setUserAccount2(this);

	return blacklists2;
    }

    public Blacklist removeBlacklists2(Blacklist blacklists2) {
	getBlacklists2().remove(blacklists2);
	blacklists2.setUserAccount2(null);

	return blacklists2;
    }

    // bi-directional many-to-one association to Friendlist
    @OneToMany(mappedBy = "userAccount1")
    public List<Friendlist> getFriendlists1() {
	return this.friendlists1;
    }

    public void setFriendlists1(List<Friendlist> friendlists1) {
	this.friendlists1 = friendlists1;
    }

    public Friendlist addFriendlists1(Friendlist friendlists1) {
	getFriendlists1().add(friendlists1);
	friendlists1.setUserAccount1(this);

	return friendlists1;
    }

    public Friendlist removeFriendlists1(Friendlist friendlists1) {
	getFriendlists1().remove(friendlists1);
	friendlists1.setUserAccount1(null);

	return friendlists1;
    }

    // bi-directional many-to-one association to Friendlist
    @OneToMany(mappedBy = "userAccount2")
    public List<Friendlist> getFriendlists2() {
	return this.friendlists2;
    }

    public void setFriendlists2(List<Friendlist> friendlists2) {
	this.friendlists2 = friendlists2;
    }

    public Friendlist addFriendlists2(Friendlist friendlists2) {
	getFriendlists2().add(friendlists2);
	friendlists2.setUserAccount2(this);

	return friendlists2;
    }

    public Friendlist removeFriendlists2(Friendlist friendlists2) {
	getFriendlists2().remove(friendlists2);
	friendlists2.setUserAccount2(null);

	return friendlists2;
    }

}