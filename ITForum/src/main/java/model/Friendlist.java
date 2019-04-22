package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the friendlists database table.
 * 
 */
@Entity
@Table(name="friendlists")
@NamedQueries({
    @NamedQuery(name="Friendlist.findAll", query="SELECT f FROM Friendlist f")    
})
public class Friendlist implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private UserAccount userAccount1;
	private UserAccount userAccount2;

	public Friendlist() {
	}


	@Id
	@GeneratedValue(generator = "my_fl_seq")
	@SequenceGenerator(name = "my_fl_seq", sequenceName = "friendlists_id_seq", allocationSize = 1)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	//bi-directional many-to-one association to UserAccount
	@ManyToOne
	@JoinColumn(name="fuid")
	public UserAccount getUserAccount1() {
		return this.userAccount1;
	}

	public void setUserAccount1(UserAccount userAccount1) {
		this.userAccount1 = userAccount1;
	}


	//bi-directional many-to-one association to UserAccount
	@ManyToOne
	@JoinColumn(name="uid")
	public UserAccount getUserAccount2() {
		return this.userAccount2;
	}

	public void setUserAccount2(UserAccount userAccount2) {
		this.userAccount2 = userAccount2;
	}

}