package validators;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import services.UserAccountService;

@Named
public class UniqueEmailValidator
	implements ConstraintValidator<UniqueEmail, String> {
    @Inject
    private UserAccountService service;

    @Override
    public boolean isValid(String value,
	    ConstraintValidatorContext context) {
	return service.isAccountFree(value, true);
    }
    
   

}
