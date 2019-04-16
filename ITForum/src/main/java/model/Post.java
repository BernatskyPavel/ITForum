package model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the posts database table.
 * 
 */
@Entity
@Table(name="posts")
@NamedQuery(name="Post.findAll", query="SELECT p FROM Post p")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String contents;
	private Timestamp created;
	private Status statusBean;
	private Thread thread;
	private UserAccount userAccount;

	public Post() {
	}
	
	@Id
	@GeneratedValue(generator="my_post_seq")
	@SequenceGenerator(name="my_post_seq", sequenceName="posts_id_seq", allocationSize=1)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Column(insertable = false)
	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
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


	//bi-directional many-to-one association to Thread
	@ManyToOne
	public Thread getThread() {
		return this.thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
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