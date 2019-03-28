package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the emails database table.
 * 
 */
@Entity
@Table(name="emails")
@NamedQuery(name="Email.findAll", query="SELECT e FROM Email e")
public class Email implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String header;
	private Timestamp sended;
	private Integer status;
	private String text;
	private UserAccount userAccount1;
	private UserAccount userAccount2;

	public Email() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getHeader() {
		return this.header;
	}

	public void setHeader(String header) {
		this.header = header;
	}


	public Timestamp getSended() {
		return this.sended;
	}

	public void setSended(Timestamp sended) {
		this.sended = sended;
	}


	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}


	//bi-directional many-to-one association to UserAccount
	@ManyToOne
	@JoinColumn(name="from_id")
	public UserAccount getUserAccount1() {
		return this.userAccount1;
	}

	public void setUserAccount1(UserAccount userAccount1) {
		this.userAccount1 = userAccount1;
	}


	//bi-directional many-to-one association to UserAccount
	@ManyToOne
	@JoinColumn(name="to_id")
	public UserAccount getUserAccount2() {
		return this.userAccount2;
	}

	public void setUserAccount2(UserAccount userAccount2) {
		this.userAccount2 = userAccount2;
	}

}