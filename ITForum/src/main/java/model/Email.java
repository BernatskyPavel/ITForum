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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The persistent class for the emails database table.
 * 
 */
@Entity
@Table(name = "emails")
@NamedQuery(name = "Email.findAll", query = "SELECT e FROM Email e")
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String header;
    private Timestamp sended;
    private String text;
    private UserAccount userAccount1;
    private UserAccount userAccount2;
    private Status statusBean;

    public Email() {
    }

    @Id
    @GeneratedValue(generator = "my_email_seq")
    @SequenceGenerator(name = "my_email_seq", sequenceName = "emails_id_seq", allocationSize = 1)
    public Integer getId() {
	return this.id;
    }
    
    public void setId(Integer id) {
	this.id = id;
    }
    @NotNull
    @Size(max = 255)
    public String getHeader() {
	return this.header;
    }

    public void setHeader(String header) {
	this.header = header;
    }

    @Column(insertable = false)
    public Timestamp getSended() {
	return this.sended;
    }

    public void setSended(Timestamp sended) {
	this.sended = sended;
    }

    @NotNull
    @Size(max = 2000)
    public String getText() {
	return this.text;
    }

    public void setText(String text) {
	this.text = text;
    }

    // bi-directional many-to-one association to UserAccount
    @ManyToOne
    @JoinColumn(name = "from_id")
    public UserAccount getUserAccount1() {
	return this.userAccount1;
    }

    public void setUserAccount1(UserAccount userAccount1) {
	this.userAccount1 = userAccount1;
    }

    // bi-directional many-to-one association to UserAccount
    @ManyToOne
    @JoinColumn(name = "to_id")
    public UserAccount getUserAccount2() {
	return this.userAccount2;
    }

    public void setUserAccount2(UserAccount userAccount2) {
	this.userAccount2 = userAccount2;
    }

    // bi-directional many-to-one association to Status
    @ManyToOne
    @JoinColumn(name = "status")
    public Status getStatusBean() {
	return this.statusBean;
    }

    public void setStatusBean(Status statusBean) {
	this.statusBean = statusBean;
    }
}