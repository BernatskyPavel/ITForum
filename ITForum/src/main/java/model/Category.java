package model;

import java.io.Serializable;
import javax.persistence.*;

import services.StatusService;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The persistent class for the categories database table.
 * 
 */
@Entity
@Table(name="categories")
@NamedQueries({
    @NamedQuery(name="Category.findAll", query="SELECT c FROM Category c"),
    @NamedQuery(name="Category.findById", query="SELECT c FROM Category c where c.id = :id")
})

public class Category implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Timestamp created;
	private String description;
	private String name;
	private Category category;
	private List<Category> categories;
	private Section sectionBean;
	private Status statusBean;
	private UserAccount userAccount;
	private List<Thread> threads;

	public Category() {
	}

	public List<Thread> getApprovedThreads(){
	    return threads.stream().filter(x->
		StatusService.Statuses.APPROVED.getId()  == x.getStatusBean().getId().intValue() ||
		StatusService.Statuses.COMPLETED.getId() == x.getStatusBean().getId().intValue() ||
		StatusService.Statuses.SPAM.getId()      == x.getStatusBean().getId().intValue()
	    ).collect(Collectors.toList());
	}
	
	public List<Thread> getBlockedThreads(){
	    return threads.stream().filter(x->
		StatusService.Statuses.BLOCKED.getId()  == x.getStatusBean().getId().intValue() ||
		StatusService.Statuses.WAITING.getId() == x.getStatusBean().getId().intValue()
	    ).collect(Collectors.toList());
	}
	
	public List<Category> getApprovedSubCategories(){
	    return categories.stream().filter(x->
		StatusService.Statuses.APPROVED.getId()  == x.getStatusBean().getId().intValue() ||
		StatusService.Statuses.COMPLETED.getId() == x.getStatusBean().getId().intValue() ||
		StatusService.Statuses.SPAM.getId()      == x.getStatusBean().getId().intValue()
	    ).collect(Collectors.toList());
	}
	
	public int getPostsNumber() {
	    List<Thread> threads = getApprovedThreads();
	    return threads.stream().map(x->x.getApprovedPosts().size()).mapToInt(Integer::intValue).sum();
	}
	
	public Post getLastPost() {
	    List<Thread> threads = getApprovedThreads();
	    Comparator<Post> cmp = Comparator.comparing(Post::getCreated);
	    List<Post> posts = threads.stream().map(Thread::getLastPost).filter(x->x!=null).collect(Collectors.toList());
	    if(null == posts || posts.isEmpty()) {
		return null;
	    }
	    return posts.stream().max(cmp).orElse(null);
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Timestamp getCreated() {
		return this.created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//bi-directional many-to-one association to Category
	@ManyToOne
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	//bi-directional many-to-one association to Category
	@OneToMany(mappedBy="category")
	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Category addCategory(Category category) {
		getCategories().add(category);
		category.setCategory(this);

		return category;
	}

	public Category removeCategory(Category category) {
		getCategories().remove(category);
		category.setCategory(null);

		return category;
	}


	//bi-directional many-to-one association to Section
	@ManyToOne
	@JoinColumn(name="section")
	public Section getSectionBean() {
		return this.sectionBean;
	}

	public void setSectionBean(Section sectionBean) {
		this.sectionBean = sectionBean;
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
	@JoinColumn(name="creator")
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}


	//bi-directional many-to-one association to Thread
	@OneToMany(mappedBy="categoryBean")
	public List<Thread> getThreads() {
		return this.threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	public Thread addThread(Thread thread) {
		getThreads().add(thread);
		thread.setCategoryBean(this);

		return thread;
	}

	public Thread removeThread(Thread thread) {
		getThreads().remove(thread);
		thread.setCategoryBean(null);

		return thread;
	}

}