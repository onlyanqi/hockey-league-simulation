package validator;

import java.util.List;

public class Validation implements IValidation {

    public boolean isListNotEmpty(List list){
        boolean isNotEmpty = true;

        if(list == null || list.isEmpty()){
            isNotEmpty = false;
        }

        return isNotEmpty;
    }

    public boolean isNotNull(Object input){
        boolean isNotNull = true;

        if(input == null){
            isNotNull = false;
        }

        return isNotNull;
    }

    public boolean isNotEmpty(String input){
        boolean isNotEmpty = true;

        if(input == null || input.isEmpty()){
            isNotEmpty = false;
        }

        return isNotEmpty;
    }

}
