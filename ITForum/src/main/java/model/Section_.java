package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2019-03-05T23:59:26.750+0300")
@StaticMetamodel(Section.class)
public class Section_ {
	public static volatile SingularAttribute<Section, Integer> id;
	public static volatile ListAttribute<Section, Category> categories;
	public static volatile SingularAttribute<Section, Status> statusBean;
	public static volatile SingularAttribute<Section, UserAccount> userAccount;
	public static volatile SingularAttribute<Section, String> name;
}
