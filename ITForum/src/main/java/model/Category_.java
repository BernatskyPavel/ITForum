package model;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-03-05T23:59:26.735+0300")
@StaticMetamodel(Category.class)
public class Category_ {
	public static volatile SingularAttribute<Category, Integer> id;
	public static volatile SingularAttribute<Category, Category> category;
	public static volatile ListAttribute<Category, Category> categories;
	public static volatile SingularAttribute<Category, Section> sectionBean;
	public static volatile SingularAttribute<Category, Status> statusBean;
	public static volatile SingularAttribute<Category, UserAccount> userAccount;
	public static volatile ListAttribute<Category, Thread> threads;
	public static volatile SingularAttribute<Category, Timestamp> created;
	public static volatile SingularAttribute<Category, String> description;
	public static volatile SingularAttribute<Category, String> name;
}
