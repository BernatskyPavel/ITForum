package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-03-05T23:59:26.769+0300")
@StaticMetamodel(UserStatus.class)
public class UserStatus_ {
	public static volatile SingularAttribute<UserStatus, Integer> id;
	public static volatile ListAttribute<UserStatus, UserAccount> userAccounts;
	public static volatile SingularAttribute<UserStatus, String> name;
}
