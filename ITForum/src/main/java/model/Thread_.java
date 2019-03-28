package model;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-03-05T23:59:26.760+0300")
@StaticMetamodel(Thread.class)
public class Thread_ {
	public static volatile SingularAttribute<Thread, Integer> id;
	public static volatile ListAttribute<Thread, Post> posts;
	public static volatile SingularAttribute<Thread, Category> categoryBean;
	public static volatile SingularAttribute<Thread, Status> statusBean;
	public static volatile SingularAttribute<Thread, UserAccount> userAccount;
	public static volatile SingularAttribute<Thread, String> content;
	public static volatile SingularAttribute<Thread, Timestamp> created;
	public static volatile SingularAttribute<Thread, String> subject;
}
