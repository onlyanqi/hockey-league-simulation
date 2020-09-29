package common;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final String dbFile = ".dev.properties";
    public static final String dbUserName = "db.User";
    public static final String dbPassword = "db.Password";
    public static final String dbHost = "db.url";
    public static final String dbName = "db.Name";
    public static final String dbPort = "db.Port";
    public static final String semiColon = ":";
    public static final String forwardSlash = "/";
    public static final List<String> playerRoles = new ArrayList<>(List.of("goalie", "forward", "defense"));

}
