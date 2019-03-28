package model;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-03-05T23:59:26.741+0300")
@StaticMetamodel(Email.class)
public class Email_ {
	public static volatile SingularAttribute<Email, Integer> id;
	public static volatile SingularAttribute<Email, UserAccount> userAccount1;
	public static volatile SingularAttribute<Email, UserAccount> userAccount2;
	public static volatile SingularAttribute<Email, String> header;
	public static volatile SingularAttribute<Email, Timestamp> sended;
	public static volatile SingularAttribute<Email, Integer> status;
	public static volatile SingularAttribute<Email, String> text;
}
