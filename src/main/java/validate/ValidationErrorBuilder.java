package validate;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

/**
 * Transforms errors to a human readable form.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
public class ValidationErrorBuilder {

    /**
     * Validates error and returns human readable error text.
     * 
     * @param errors
     * @return short text about the error.
     */
    public static ValidationError fromBindingErrors(Errors errors) {
        ValidationError error = new ValidationError("Validation failed. " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}
