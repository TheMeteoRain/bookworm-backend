package validate;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represent error messages.
 * 
 * @version 2017.0522
 * @author Akash Singh akash.singh@cs.tamk.fi
 * @since 1.7
 */
public class ValidationError {

    /**
     * Holds all errors.
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errors = new ArrayList<>();

    /**
     * Error message.
     */
    private final String errorMessage;

    /**
     * Creates a new ValidationError with the given error message.
     * 
     * @param errorMessage error message to be shown.
     */
    public ValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Add a validation error to this validation error's error list.
     * 
     * @param error error to be added to the list.
     */
    public void addValidationError(String error) {
        errors.add(error);
    }

    /**
     * Get the list of errors this validation error has.
     * 
     * @return this validation error's error list.
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * Gets the error message of this validation error.
     * 
     * @return this validation error's error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}
