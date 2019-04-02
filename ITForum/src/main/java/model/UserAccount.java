	package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the user_accounts database table.
 * 
 */
@Entity
@Table(name="user_accounts")
@NamedQuery(name="UserAccount.findAll", query="SELECT u FROM UserAccount u")
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
	private Boolean isAdmin;
	private Boolean isModerator;
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

	public UserAccount() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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


	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name="first_name")
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


	@Column(name="hashed_password")
	public String getHashedPassword() {
		return this.hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}


	@Column(name="is_admin")
	public Boolean getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	@Column(name="is_moderator")
	public Boolean getIsModerator() {
		return this.isModerator;
	}

	public void setIsModerator(Boolean isModerator) {
		this.isModerator = isModerator;
	}


	@Column(name="last_activity")
	public Timestamp getLastActivity() {
		return this.lastActivity;
	}

	public void setLastActivity(Timestamp lastActivity) {
		this.lastActivity = lastActivity;
	}


	@Column(name="last_name")
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	@Column(name="middle_name")
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


	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	@Column(name="verify_code")
	public String getVerifyCode() {
		return this.verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}


	//bi-directional many-to-one association to Category
	@OneToMany(mappedBy="userAccount")
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


	//bi-directional many-to-one association to Email
	@OneToMany(mappedBy="userAccount1")
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


	//bi-directional many-to-one association to Email
	@OneToMany(mappedBy="userAccount2")
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


	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="userAccount")
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


	//bi-directional many-to-one association to Section
	@OneToMany(mappedBy="userAccount")
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


	//bi-directional many-to-one association to Thread
	@OneToMany(mappedBy="userAccount")
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


	//bi-directional many-to-one association to UserStatus
	@ManyToOne
	@JoinColumn(name="user_status")
	public UserStatus getUserStatusBean() {
		return this.userStatusBean;
	}

	public void setUserStatusBean(UserStatus userStatusBean) {
		this.userStatusBean = userStatusBean;
	}

}