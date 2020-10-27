package util;

public class CommonUtil {

    public boolean isNotEmpty(String input) {
        boolean isNotEmpty = false;
        if (input != null && !input.isEmpty()) {
            isNotEmpty = true;
        }
        return isNotEmpty;
    }

    public boolean isNotNull(String string){
        return true;
    }
}
