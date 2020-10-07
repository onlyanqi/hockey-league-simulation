package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final String dbFile = "../.properties";
    public static final String dbUserName = "db.User";
    public static final String dbPassword = "db.Password";
    public static final String dbHost = "db.url";
    public static final String dbName = "db.Name";
    public static final String dbPort = "db.Port";
    public static final String semiColon = ":";
    public static final String forwardSlash = "/";
    public static final List<String> playerPositions = new ArrayList<>(Arrays.asList("goalie", "forward", "defense"));
    public static final String timezone="?" +
            "useUnicode=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static final String CALL = "call";
    public static final String space = " ";

    public static final String loadUserByName = "LoadUserByName(?,?,?)";
    public static final String loadLeagueByName = "LoadLeagueByName(?,?,?,?)";
    public static final String loadConferenceByName = "LoadConferenceByName(?,?,?,?)";
    public static final String loadDivisionByName = "LoadDivisionByName(?,?,?,?)";
    public static final String loadSeasonByName = "LoadSeasonByName(?,?,?)";
    public static final String loadTeamByName = "LoadTeamByName(?,?,?,?)";
    public static final String loadFreeAgentByLeagueId = "LoadFreeAgentByName(?,?,?,?)";
    public static final String loadPlayerByName = "LoadPlayerByName(?,?,?,?,?)";

    public static final String addUser = "AddUser(?,?,?)";
    public static final String addLeague = "AddLeague(?,?,?)";
    public static final String addConference = "AddConference(?,?,?)";
    public static final String addDivision = "AddDivision(?,?,?)";
    public static final String addSeason = "AddSeason(?,?)";
    public static final String addTeam = "AddTeam(?,?,?)";
    public static final String addFreeAgent = "AddFreeAgent(?,?,?)";
    public static final String addPlayer = "AddPlayer(?,?,?,?,?,?)";



}
