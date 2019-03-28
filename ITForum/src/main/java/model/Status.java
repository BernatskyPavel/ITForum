package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the statuses database table.
 * 
 */
@Entity
@Table(name="statuses")
@NamedQuery(name="Status.findAll", query="SELECT s FROM Status s")
public class Status implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private List<Category> categories;
	private List<Post> posts;
	private List<Section> sections;
	private List<Thread> threads;

	public Status() {
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


	//bi-directional many-to-one association to Category
	@OneToMany(mappedBy="statusBean")
	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public Category addCategory(Category category) {
		getCategories().add(category);
		category.setStatusBean(this);

		return category;
	}

	public Category removeCategory(Category category) {
		getCategories().remove(category);
		category.setStatusBean(null);

		return category;
	}


	//bi-directional many-to-one association to Post
	@OneToMany(mappedBy="statusBean")
	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Post addPost(Post post) {
		getPosts().add(post);
		post.setStatusBean(this);

		return post;
	}

	public Post removePost(Post post) {
		getPosts().remove(post);
		post.setStatusBean(null);

		return post;
	}


	//bi-directional many-to-one association to Section
	@OneToMany(mappedBy="statusBean")
	public List<Section> getSections() {
		return this.sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public Section addSection(Section section) {
		getSections().add(section);
		section.setStatusBean(this);

		return section;
	}

	public Section removeSection(Section section) {
		getSections().remove(section);
		section.setStatusBean(null);

		return section;
	}


	//bi-directional many-to-one association to Thread
	@OneToMany(mappedBy="statusBean")
	public List<Thread> getThreads() {
		return this.threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	public Thread addThread(Thread thread) {
		getThreads().add(thread);
		thread.setStatusBean(this);

		return thread;
	}

	public Thread removeThread(Thread thread) {
		getThreads().remove(thread);
		thread.setStatusBean(null);

		return thread;
	}

}