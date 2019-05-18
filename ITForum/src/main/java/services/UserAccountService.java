package services;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import model.Status;
import model.Thread;
import model.UserAccount;
import model.UserStatus;

@ApplicationScoped
public class UserAccountService implements IService<UserAccount> {
    @Inject
    private DbService DbManager;
    private EmailValidator emailValidator;

    @PostConstruct
    public void init() {
	emailValidator = EmailValidator.getInstance(false);
    }

    public UserAccount getByLogin(String login) {
	UserAccount user = null;
	EntityManager manager = DbManager.getManager();
	String queryName = StringUtils.EMPTY,
		queryParamName = StringUtils.EMPTY;

	if (emailValidator.isValid(login)) {
	    queryName = "UserAccount.findByEmail";
	    queryParamName = "email";
	} else {
	    queryName = "UserAccount.findByUsername";
	    queryParamName = "username";
	}

	System.out
		.println(queryName + "-" + queryParamName + "-DEBUG");
	manager.getTransaction().begin();
	TypedQuery<UserAccount> query = manager
		.createNamedQuery(queryName, UserAccount.class);
	query.setParameter(queryParamName, login);
	try {
	    user = query.getSingleResult();
	    manager.refresh(user);
	    System.out.println(user.getUsername());
	} catch (Exception e) {
	    user = null;
	    System.out.println(e.getMessage());
	}
	manager.getTransaction().commit();
	manager.close();
	return user;
    }

    @Override
    public List<UserAccount> getAll() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public UserAccount getById(Object Id) {
	UserAccount user = null;
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	TypedQuery<UserAccount> query = manager.
		      createNamedQuery("UserAccount.findById", UserAccount.class);
	query.setParameter("id", ((Integer)Id).intValue());
	try {
	    user = query.getSingleResult();
	} catch (Exception e) {
	    user = null;
	}
	manager.getTransaction().commit();
	manager.close();
	return user;
    }

    @Override
    public void deleteById(Object Id) {
	// TODO Auto-generated method stub

    }

    @Override
    public UserAccount create(UserAccount entity) {
	UserStatus status = null;
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	TypedQuery<UserStatus> statusQuery = manager.createNamedQuery(
		"UserStatus.findById", UserStatus.class);
	statusQuery.setParameter("id",
		UserStatusService.Statuses.EMAIL_NOT_VERIFIED
			.getId());
	try {
	    status = statusQuery.getSingleResult();
	} catch (Exception e) {
	    status = null;
	}
	if (status == null) {
	    manager.getTransaction().rollback();
	    manager.close();
	    return null;
	}
	status.addUserAccount(entity);
	entity.setUserStatusBean(status);
	try {
	    manager.persist(entity);
	} catch (Exception e) {
	    e.printStackTrace();
	    manager.getTransaction().rollback();
	    manager.close();
	    return null;
	}
	manager.flush();
	manager.getTransaction().commit();
	manager.close();
	return entity;
    }

    @Override
    public UserAccount update(UserAccount entity) {
	// TODO Auto-generated method stub
	return null;
    }

    public void flushChanges(UserAccount entity) {
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	manager.merge(entity);
	manager.flush();
	manager.getTransaction().commit();
	manager.close();
    }

    public void verifyUser(Integer userId, String verifyCode,
	    UserStatus status) {
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	UserAccount user = this.getById(userId);
	if (user != null) {
	    if (user.getVerifyCode().equals(verifyCode)) {
		user.getUserStatusBean().removeUserAccount(user);
		status.addUserAccount(user);
		user.setUserStatusBean(status);
		user.setVerifyCode(null);
	    }
	}
	manager.merge(user);
	manager.flush();
	manager.getTransaction().commit();
    }
    
    public UserAccount changeStatus(UserAccount entity, int sid) {
	UserStatus status = null;
	EntityManager manager = DbManager.getManager();
	manager.getTransaction().begin();
	TypedQuery<UserStatus> statusQuery = manager.createNamedQuery(
		"UserStatus.findById", UserStatus.class);
	statusQuery.setParameter("id",sid);
	try {
	    status = statusQuery.getSingleResult();
	} catch (Exception e) {
	    status = null;
	}
	if (status == null) {
	    manager.getTransaction().rollback();
	    manager.close();
	    return null;
	}
	try {
	    
	    entity.getUserStatusBean().removeUserAccount(entity);
	    entity.setUserStatusBean(status);
	    entity.getUserStatusBean().addUserAccount(entity);
	    manager.merge(entity);
	    manager.flush();
	} catch (Exception e) {
	    e.printStackTrace();
	    manager.getTransaction().rollback();
	    manager.close();
	    return null;
	}
	manager.getTransaction().commit();
	manager.close();
	return entity;
    }
    /**
     * 
     * @param fieldValue Value of a field
     * @param isEmail    If the value is set to <b>false</b>, the method will check
     *                   the availability of the account by the <b>username</b>
     *                   field
     * @return Is there an account with such <b>username</b> or <b>email</b>
     */
    public boolean isAccountFree(String fieldValue, boolean isEmail) {
	String queryName = StringUtils.EMPTY,
		queryParamName = StringUtils.EMPTY;

	if (isEmail) {
	    queryName = "UserAccount.countByEmail";
	    queryParamName = "email";
	} else {
	    queryName = "UserAccount.countByUsername";
	    queryParamName = "username";
	}

	EntityManager manager = DbManager.getManager();
	TypedQuery<Long> userQuery = manager
		.createNamedQuery(queryName, Long.class);
	userQuery.setParameter(queryParamName, fieldValue);
	Long result = userQuery.getSingleResult();
	manager.close();
	System.out.println(result);
	if (result != 0) {
	    return false;
	}
	System.out.println("true");
	return true;
    }
}
