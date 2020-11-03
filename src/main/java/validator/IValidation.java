package validator;

import java.util.List;

public interface IValidation {

    boolean isListNotEmpty(List list);

    boolean isNotNull(Object input);

    boolean isNotEmpty(String input);

}
