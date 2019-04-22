package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-21T21:17:53.799+0300")
@StaticMetamodel(Status.class)
public class Status_ {
	public static volatile SingularAttribute<Status, Integer> id;
	public static volatile ListAttribute<Status, Category> categories;
	public static volatile ListAttribute<Status, Post> posts;
	public static volatile ListAttribute<Status, Section> sections;
	public static volatile ListAttribute<Status, Thread> threads;
	public static volatile ListAttribute<Status, Email> emails;
	public static volatile SingularAttribute<Status, String> name;
}
