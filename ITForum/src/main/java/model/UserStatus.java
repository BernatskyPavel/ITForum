package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user_statuses database table.
 * 
 */
@Entity
@Table(name="user_statuses")
@NamedQuery(name="UserStatus.findAll", query="SELECT u FROM UserStatus u")
public class UserStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private List<UserAccount> userAccounts;

	public UserStatus() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//bi-directional many-to-one association to UserAccount
	@OneToMany(mappedBy="userStatusBean")
	public List<UserAccount> getUserAccounts() {
		return this.userAccounts;
	}

	public void setUserAccounts(List<UserAccount> userAccounts) {
		this.userAccounts = userAccounts;
	}

	public UserAccount addUserAccount(UserAccount userAccount) {
		getUserAccounts().add(userAccount);
		userAccount.setUserStatusBean(this);

		return userAccount;
	}

	public UserAccount removeUserAccount(UserAccount userAccount) {
		getUserAccounts().remove(userAccount);
		userAccount.setUserStatusBean(null);

		return userAccount;
	}

}