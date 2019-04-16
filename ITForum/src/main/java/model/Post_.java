package model;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-16T19:56:48.771+0300")
@StaticMetamodel(Post.class)
public class Post_ {
	public static volatile SingularAttribute<Post, Integer> id;
	public static volatile SingularAttribute<Post, Timestamp> created;
	public static volatile SingularAttribute<Post, Status> statusBean;
	public static volatile SingularAttribute<Post, Thread> thread;
	public static volatile SingularAttribute<Post, UserAccount> userAccount;
	public static volatile SingularAttribute<Post, String> contents;
}
