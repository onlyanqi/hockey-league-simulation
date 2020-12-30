package persistance.serializers.ModelsForDeserialization.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeagueDeserializationModel extends SharedAttributes {

    public int createdBy;
    public String user;
    public String userCreatedTeamName;
    public List<Conference> conferenceList;
    public List<Coach> coachList;
    public List<Manager> managerList;
    public List<Player> retiredPlayerList;
    public List<Player> draftedPlayerList;
    public FreeAgent freeAgent;
    public LocalDate currentDate;
    public GamePlayConfig gamePlayConfig;
    public GameSchedule games;
    public TeamStanding regularSeasonStanding;
    public TeamStanding playOffStanding;
    public TeamStanding activeTeamStanding;
    public ArrayList<TeamStat> teamStats;
    public NHLEvents nhlEvents;
    public List<TradeOffer> tradeOfferList;
    public List<Trophy> historicalTrophyList;
    public Trophy trophy;
}
