package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

import services.StatusService;


/**
 * The persistent class for the threads database table.
 * 
 */
@Entity
@Table(name="threads")
@NamedQueries({
    @NamedQuery(name="Thread.findAll", query="SELECT t FROM Thread t"),
    @NamedQuery(name="Thread.findById", query="SELECT t FROM Thread t where t.id = :id")
})

public class Thread implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String content;
	private Timestamp created;
	private String subject;
	private List<Post> posts;
	private Category categoryBean;
	private Status statusBean;
	private UserAccount userAccount;

	public Thread() {
	}
	@Transient
	public List<Post> getApprovedPosts(){
	    return posts.stream().filter(x->
		StatusService.Statuses.APPROVED.getId()  == x.getStatusBean().getId().intValue() ||
		StatusService.Statuses.COMPLETED.getId() == x.getStatusBean().getId().intValue() ||
		StatusService.Statuses.SPAM.getId()      == x.getStatusBean().getId().intValue()
	    ).collect(Collectors.toList());
	}
	@Transient
	public Post getLastPost() {
	    Comparator<Post> cmp = Comparator.comparing(Post::getCreated);
	    return getApprovedPosts().stream().max(cmp).orElse(null);
	}
	
	@Transient
	public boolean isCompleted() {
	    return this.statusBean.getId().intValue() == StatusService.Statuses.COMPLETED.getId();
	}
	
	@Transient
	public boolean isBlocked() {
	    return this.statusBean.getId().intValue() == StatusService.Statuses.BLOCKED.getId();
	}
	
	@Transient
	public boolean isWaiting() {
	    return this.statusBean.getId().intValue() == StatusService.Statuses.WAITING.getId();
	}
	
	@Id
	@GeneratedValue(generator="my_thread_seq")
	@SequenceGenerator(name="my_thread_seq", sequenceName="threads_id_seq", allocationSize=1)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(insertable = false)
	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}


	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}


	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="thread")
	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post addPost(Post post) {
		getPosts().add(post);
		post.setThread(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setThread(null);

		return post;
	}


	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category")
	public Category getCategoryBean() {
		return this.categoryBean;
	}

	public void setCategoryBean(Category categoryBean) {
		this.categoryBean = categoryBean;
	}


	//bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name="status")
	public Status getStatusBean() {
		return this.statusBean;
	}

	public void setStatusBean(Status statusBean) {
		this.statusBean = statusBean;
	}


	//bi-directional many-to-one association to UserAccount
	@ManyToOne
	@JoinColumn(name="user_account_id")
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

}