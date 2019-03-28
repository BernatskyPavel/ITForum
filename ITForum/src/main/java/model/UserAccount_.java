package model;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-03-05T23:59:26.764+0300")
@StaticMetamodel(UserAccount.class)
public class UserAccount_ {
	public static volatile SingularAttribute<UserAccount, Integer> id;
	public static volatile SingularAttribute<UserAccount, String> firstName;
	public static volatile SingularAttribute<UserAccount, String> hashedPassword;
	public static volatile SingularAttribute<UserAccount, Boolean> isAdmin;
	public static volatile SingularAttribute<UserAccount, Boolean> isModerator;
	public static volatile SingularAttribute<UserAccount, Timestamp> lastActivity;
	public static volatile SingularAttribute<UserAccount, String> lastName;
	public static volatile SingularAttribute<UserAccount, String> middleName;
	public static volatile SingularAttribute<UserAccount, String> verifyCode;
	public static volatile ListAttribute<UserAccount, Category> categories;
	public static volatile ListAttribute<UserAccount, Email> emails1;
	public static volatile ListAttribute<UserAccount, Email> emails2;
	public static volatile ListAttribute<UserAccount, Post> posts;
	public static volatile ListAttribute<UserAccount, Section> sections;
	public static volatile ListAttribute<UserAccount, Thread> threads;
	public static volatile SingularAttribute<UserAccount, UserStatus> userStatusBean;
	public static volatile SingularAttribute<UserAccount, String> about;
	public static volatile SingularAttribute<UserAccount, String> adress;
	public static volatile SingularAttribute<UserAccount, Timestamp> birthday;
	public static volatile SingularAttribute<UserAccount, Timestamp> created;
	public static volatile SingularAttribute<UserAccount, String> email;
	public static volatile SingularAttribute<UserAccount, String> gender;
	public static volatile SingularAttribute<UserAccount, String> picture;
	public static volatile SingularAttribute<UserAccount, String> status;
	public static volatile SingularAttribute<UserAccount, String> username;
}
