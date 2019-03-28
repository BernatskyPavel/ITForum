package model;

import java.io.Serializable;
import javax.persistence.*;

import services.StatusService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The persistent class for the sections database table.
 * 
 */
@Entity
@Table(name = "sections")
@NamedQueries({
	@NamedQuery(name = "Section.findAll", query = "SELECT s FROM Section s")})
public class Section implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private List<Category> categories;
    private Status statusBean;
    private UserAccount userAccount;

    public Section() {
    }

    public List<Category> getApprovedCategories(){
	return categories.stream().filter(x->
		StatusService.Statuses.APPROVED.getId() == x.getStatusBean().getId().intValue() ||
		StatusService.Statuses.COMPLETED.getId() == x.getStatusBean().getId().intValue() ||
		StatusService.Statuses.SPAM.getId() == x.getStatusBean().getId().intValue()
	).collect(Collectors.toList());
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    // bi-directional many-to-one association to Category
    @OneToMany(mappedBy = "sectionBean")
    public List<Category> getCategories() {
	return this.categories;
    }

    public void setCategories(List<Category> categories) {
	this.categories = categories;
    }

    public Category addCategory(Category category) {
	getCategories().add(category);
	category.setSectionBean(this);

	return category;
    }

    public Category removeCategory(Category category) {
	getCategories().remove(category);
	category.setSectionBean(null);

	return category;
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

    // bi-directional many-to-one association to UserAccount
    @ManyToOne
    @JoinColumn(name = "user_account_id")
    public UserAccount getUserAccount() {
	return this.userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
	this.userAccount = userAccount;
    }

}