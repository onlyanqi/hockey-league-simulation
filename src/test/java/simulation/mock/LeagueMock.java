package simulation.mock;

import db.data.*;
import org.json.simple.JSONObject;
import simulation.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeagueMock implements ILeagueDao {
    static final String FREEAGENT = "FreeAgent";
    List<Player> playerList = new ArrayList<>();
    IPlayerDao playerFactory = new PlayerMock();

    public List<IConference> formConferenceList() throws Exception {
        List<IConference> conferenceList = new ArrayList<>();

        IConferenceDao conferenceFactory = new ConferenceMock();
        IConference conference = new Conference(1, conferenceFactory);
        conferenceList.add(conference);

        conference = new Conference(2, conferenceFactory);
        conferenceList.add(conference);

        return conferenceList;
    }

    public List<IConference> formCreateTeamConferenceList() throws Exception {
        List<IConference> conferenceList = new ArrayList<>();

        IConferenceDao conferenceFactory = new ConferenceMock();
        Conference conference = new Conference(1, conferenceFactory);
        conferenceList.add(conference);

        conference = new Conference(4, conferenceFactory);
        conferenceList.add(conference);

        return conferenceList;
    }

    public List<ICoach> formCoachList() throws Exception {
        List<ICoach> coachList = new ArrayList<>();

        ICoachDao coachFactory = new CoachMock();

        for (int i = 0; i < 5; i++) {
            Coach coach = new Coach(i, coachFactory);
            coachList.add(coach);
            coachList.add(coach);
        }

        return coachList;
    }

    public List<IManager> formManagerList() throws Exception {
        List<IManager> managerList = new ArrayList<>();

        IManagerDao managerFactory = new ManagerMock();

        for (int i = 1; i < 6; i++) {
            Manager manager = new Manager(i, managerFactory);
            managerList.add(manager);
        }
        return managerList;
    }

    public IGameSchedule formGames() throws Exception {
        IGameSchedule games = new GameSchedule();
        List<IGame> gameList = new ArrayList<>();

        IGameDao gameFactory = new GameMock();

        for (int i = 0; i < 4; i++) {
            IGame game = new Game(i, gameFactory);
            gameList.add(game);
        }
        games.setGameList(gameList);
        return games;
    }

    public ITeamStanding formTeamStanding() throws Exception {
        ITeamStanding teamStanding = new TeamStanding();
        List<ITeamScore> teamScoreList = new ArrayList<>();

        ITeamScoreDao teamScoreFactory = new TeamScoreMock();

        for (int i = 0; i < 4; i++) {
            TeamScore teamScore = new TeamScore(i, teamScoreFactory);
            teamScoreList.add(teamScore);
        }
        teamStanding.setTeamsScoreList(teamScoreList);
        return teamStanding;
    }

    public NHLEvents formNHLEvents(int id) throws Exception {
        IEventDao eventFactory = new NHLEventMock();
        NHLEvents nhlEvents = new NHLEvents(id, eventFactory);
        return nhlEvents;
    }

    @Override
    public int addLeague(ILeague league) throws Exception {
        league = new League(1);
        return league.getId();
    }

    private FreeAgent formFreeAgent() throws Exception {
        FreeAgent freeAgent = new FreeAgent();
        freeAgent.setPlayerList(formPlayerList());
        return freeAgent;
    }

    public List formPlayerList() throws Exception {
        List<Player> playerList = new ArrayList<>();

        IPlayerDao playerFactory = new PlayerMock();
        for (int i = 1; i < 22; i++) {
            Player player = new Player(i, playerFactory);
            playerList.add(player);
        }

        return playerList;
    }

    public Trading getTrading() throws Exception {
        ITradingDao tradingFactory = new TradingMock();
        Trading trading = new Trading(1, tradingFactory);
        return trading;
    }

    public Injury getInjury() throws Exception {
        IInjuryDao injuryFactory = new InjuryMock();
        Injury injury = new Injury(1, injuryFactory);
        return injury;
    }

    public Aging getAging() throws Exception {
        IAgingDao agingFactory = new AgingMock();
        Aging aging = new Aging(1, agingFactory);
        return aging;
    }

    public Training getTraining() throws Exception {
        ITrainingDao trainingFactory = new TrainingMock();
        Training training = new Training(1, trainingFactory);
        return training;
    }

    public GameResolver getGameResolver() throws Exception {
        IGameResolverDao resolverFactory = new GameResolverMock();
        GameResolver gameResolver = new GameResolver(1, resolverFactory);
        return gameResolver;
    }

    public List<ITradeOffer> getTradeOfferList(int leagueId) throws Exception {
        ITradeOfferDao tradeOfferFactory = new TradeOfferMock();
        return tradeOfferFactory.loadTradeOfferDetailsByLeagueId(leagueId);
    }


    public IGamePlayConfig formGamePlayConfig() throws Exception {
        IGamePlayConfig gamePlayConfig = new GamePlayConfig();
        gamePlayConfig.setTrading(getTrading());
        gamePlayConfig.setAging(getAging());
        gamePlayConfig.setInjury(getInjury());
        gamePlayConfig.setTraining(getTraining());
        gamePlayConfig.setGameResolver(getGameResolver());
        return gamePlayConfig;
    }

    @Override
    public void loadLeagueById(int id, ILeague league) throws Exception {

        switch (new Long(id).intValue()) {
            case 1:
                league.setName("League1");
                league.setConferenceList(formConferenceList());
                league.setFreeAgent(formFreeAgent());
                league.setCoachList(formCoachList());
                league.setGamePlayConfig(formGamePlayConfig());
                league.setManagerList(formManagerList());
                league.setTradeOfferList(getTradeOfferList(1));
                league.setCurrentDate(LocalDate.now());
                league.setGames(formGames());
                league.setActiveTeamStanding(formTeamStanding());
                league.setNhlRegularSeasonEvents(formNHLEvents(0));
                league.setGamePlayConfig(formGamePlayConfig());
                league.setCreatedBy(1);
                break;

            case 2:
                league.setName(null);
                league.setConferenceList(formConferenceList());
                league.setManagerList(formManagerList());
                league.setFreeAgent(formFreeAgent());
                league.setCoachList(formCoachList());
                league.setGamePlayConfig(formGamePlayConfig());
                league.setTradeOfferList(getTradeOfferList(2));
                league.setNhlRegularSeasonEvents(formNHLEvents(1));
                league.setGamePlayConfig(formGamePlayConfig());
                league.setCurrentDate(LocalDate.now());
                break;

            case 3:
                league.setName("Invalid Date");
                league.setConferenceList(formConferenceList());
                league.setCoachList(formCoachList());
                league.setManagerList(formManagerList());
                league.setFreeAgent(formFreeAgent());
                league.setGamePlayConfig(formGamePlayConfig());
                league.setTradeOfferList(getTradeOfferList(3));
                league.setNhlRegularSeasonEvents(formNHLEvents(2));
                league.setGamePlayConfig(formGamePlayConfig());
                league.setCurrentDate(LocalDate.now());
                break;

            case 4:
                league.setName("League4");
                league.setConferenceList(formCreateTeamConferenceList());
                league.setFreeAgent(formFreeAgent());
                league.setCoachList(formCoachList());
                league.setManagerList(formManagerList());
                league.setTradeOfferList(getTradeOfferList(1));
                league.setNhlRegularSeasonEvents(formNHLEvents(0));
                league.setGames(formGames());
                league.setGamePlayConfig(formGamePlayConfig());
                league.setCurrentDate(LocalDate.now());
                break;
            case 5:
                league.setName("League5");
                league.setConferenceList(formConferenceListForGames());
                league.setFreeAgent(formFreeAgent());
                league.setCoachList(formCoachList());
                league.setManagerList(formManagerList());
                league.setTradeOfferList(getTradeOfferList(1));
                league.setNhlRegularSeasonEvents(formNHLEvents(0));
                league.setActiveTeamStanding(formTeamStanding());
                league.setGames(formGames());
                league.setGamePlayConfig(formGamePlayConfig());
                league.setCurrentDate(LocalDate.of(2021,05,06));
                break;
        }

    }

    @Override
    public void loadLeagueByName(String leagueName, int userId, ILeague league) throws Exception {
        league.setName("League1");
        league.setConferenceList(formConferenceList());
        league.setFreeAgent(formFreeAgent());
    }

    public List formLeagueList() throws Exception {
        List<ILeague> leagueList = new ArrayList<>();

        League league = new League(1);
        league.setName("League1");
        leagueList.add(league);

        league = new League(2);
        league.setName("League2");
        leagueList.add(league);

        return leagueList;
    }

    @Override
    public List<ILeague> loadLeagueListByUserId(int userId) throws Exception {
        return formLeagueList();
    }

    @Override
    public void loadLeagueFromJSON(ILeague league, JSONObject jsonObject) {

    }

    public List<IConference> formConferenceListForGames() throws Exception {
        League league = new League(1);
        league.setName("League1");

        List<IConference> conferenceList = new ArrayList<>();
        List<IDivision> divisionList1 = new ArrayList<>();
        List<IDivision> divisionList2 = new ArrayList<>();
        List<ITeam> teamList = new ArrayList<>();

        IConference conference1 = new Conference();
        conference1.setId(1);
        conference1.setName("Eastern Conference");
        conference1.setId(league.getId());

        Conference conference2 = new Conference();
        conference2.setId(2);
        conference2.setName("Western Conference");
        conference2.setId(league.getId());


        Division div1 = new Division();
        div1.setId(1);
        div1.setName("division1");
        div1.setConferenceId(1);
        Division div2 = new Division();
        div2.setId(2);
        div2.setName("division2");
        div2.setConferenceId(2);
        Division div3 = new Division();
        div3.setId(3);
        div3.setName("division3");
        div3.setConferenceId(3);
        Division div4 = new Division();
        div4.setId(4);
        div4.setName("division4");
        div4.setConferenceId(4);
        div1.setTeamList(formTeamListDivision1ForGames());
        div2.setTeamList(formTeamListDivision2ForGames());
        div3.setTeamList(formTeamListDivision3ForGames());
        div4.setTeamList(formTeamListDivision4ForGames());

        divisionList1.add(div1);
        divisionList1.add(div2);
        divisionList2.add(div3);
        divisionList2.add(div4);

        conference1.setDivisionList(divisionList1);
        conference2.setDivisionList(divisionList2);


        conferenceList.add(conference1);
        conferenceList.add(conference2);

        return conferenceList;
    }

    private List<ITeam> formTeamListDivision1ForGames() throws Exception {

        List<ITeam> teamList = new ArrayList<>();

        ITeam team11 = new Team();
        team11.setId(11);
        team11.setName("Team11");
        team11.setDivisionId(1);
        team11.setPlayerList(addPlayerInList());


        Team team12 = new Team();
        team12.setId(12);
        team12.setName("Team12");
        team12.setPlayerList(addPlayerInList());

        Team team13 = new Team();
        team13.setId(13);
        team13.setName("Team13");
        team13.setPlayerList(addPlayerInList());

        Team team14 = new Team();
        team14.setId(14);
        team14.setName("Team14");
        team14.setPlayerList(addPlayerInList());

        Team team15 = new Team();
        team15.setId(15);
        team15.setName("Team15");
        team15.setPlayerList(addPlayerInList());

        teamList.add(team11);
        teamList.add(team12);
        teamList.add(team13);
        teamList.add(team14);
        teamList.add(team15);
        return teamList;
    }

    public List<ITeam> formTeamListDivision2ForGames() throws Exception {
        List<ITeam> teamList = new ArrayList<>();
        Team team21 = new Team();
        team21.setId(21);
        team21.setName("Team21");
        team21.setPlayerList(addPlayerInList());

        Team team22 = new Team();
        team22.setId(22);
        team22.setName("Team22");
        team22.setPlayerList(addPlayerInList());

        Team team23 = new Team();
        team23.setId(23);
        team23.setName("Team23");
        team23.setPlayerList(addPlayerInList());

        Team team24 = new Team();
        team24.setId(24);
        team24.setName("Team24");
        team24.setPlayerList(addPlayerInList());

        Team team25 = new Team();
        team25.setId(25);
        team25.setName("Team25");
        team25.setPlayerList(addPlayerInList());

        teamList.add(team21);
        teamList.add(team22);
        teamList.add(team23);
        teamList.add(team24);
        teamList.add(team25);
        return teamList;
    }

    public List<ITeam> formTeamListDivision3ForGames() throws Exception {
        List<ITeam> teamList = new ArrayList<>();
        ITeam team31 = new Team();
        team31.setId(31);
        team31.setName("Team31");
        team31.setPlayerList(addPlayerInList());

        Team team32 = new Team();
        team32.setId(22);
        team32.setName("Team32");
        team32.setPlayerList(addPlayerInList());

        Team team33 = new Team();
        team33.setId(23);
        team33.setName("Team33");
        team33.setPlayerList(addPlayerInList());
        Team team34 = new Team();
        team34.setId(24);
        team34.setName("Team34");
        team34.setPlayerList(addPlayerInList());
        Team team35 = new Team();
        team35.setId(35);
        team35.setName("Team35");
        team35.setPlayerList(addPlayerInList());
        teamList.add(team31);
        teamList.add(team32);
        teamList.add(team33);
        teamList.add(team34);
        teamList.add(team35);
        return teamList;
    }

    public List<ITeam> formTeamListDivision4ForGames() throws Exception {
        List<ITeam> teamList = new ArrayList<>();
        ITeam team41 = new Team();
        team41.setId(41);
        team41.setName("Team41");
        team41.setPlayerList(addPlayerInList());
        Team team42 = new Team();
        team42.setId(42);
        team42.setName("Team42");
        team42.setPlayerList(addPlayerInList());
        Team team43 = new Team();
        team43.setId(43);
        team43.setName("Team43");
        team43.setPlayerList(addPlayerInList());
        Team team44 = new Team();
        team44.setId(44);
        team44.setName("Team44");
        team44.setPlayerList(addPlayerInList());
        Team team45 = new Team();
        team45.setId(45);
        team45.setName("Team45");
        team45.setPlayerList(addPlayerInList());
        teamList.add(team41);
        teamList.add(team42);
        teamList.add(team43);
        teamList.add(team44);
        teamList.add(team45);
        return teamList;
    }

    private List<IPlayer> addPlayerInList() throws Exception {

        List<IPlayer> playerList = new ArrayList<>();
        Player player;
        for (int i = 1; i < 31; i++) {
            player = new Player(i, playerFactory);
            playerList.add(player);
        }

        return playerList;
    }


}
