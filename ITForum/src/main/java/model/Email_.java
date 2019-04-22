package model;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-04-21T21:17:52.664+0300")
@StaticMetamodel(Email.class)
public class Email_ {
	public static volatile SingularAttribute<Email, Integer> id;
	public static volatile SingularAttribute<Email, Timestamp> sended;
	public static volatile SingularAttribute<Email, UserAccount> userAccount1;
	public static volatile SingularAttribute<Email, UserAccount> userAccount2;
	public static volatile SingularAttribute<Email, Status> statusBean;
	public static volatile SingularAttribute<Email, String> header;
	public static volatile SingularAttribute<Email, String> text;
}
