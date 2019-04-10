package validators;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import services.UserAccountService;

@Named
public class UniqueUsernameValidator
	implements ConstraintValidator<UniqueUsername, String> {
    @Inject
    private UserAccountService service;

    @Override
    public boolean isValid(String value,
	    ConstraintValidatorContext context) {
	return service.isAccountFree(value, false);
    }


}
