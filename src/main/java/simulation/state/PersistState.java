package simulation.state;

import db.data.*;
import simulation.factory.*;
import simulation.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class PersistState implements ISimulateState{

    private HockeyContext hockeyContext;
    private League league;

    public PersistState(HockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        this.league = hockeyContext.getUser().getLeague();
    }

    @Override
    public ISimulateState action() {
        System.out.println("Saving league to DB 1 "+new Date());
        saveToPersistence(league);
        System.out.println("Saving league to DB 2 "+new Date());
        return exit();
    }

    private void saveToPersistence(League league) {

        if(todayIsStartOfSeason()){
           // persistLeagueToDB();
        }else{
            updateDataBaseWithSimulatedDate();
        }

    }

    private void updateDataBaseWithSimulatedDate() {
    }

    private void persistLeagueToDB() {
        if (league != null) {
            league.setCreatedBy(hockeyContext.getUser().getId());
            Season season = new Season();
            season.setName(String.valueOf(LocalDate.now().getYear()));

            try {
                LeagueConcrete leagueConcrete = new LeagueConcrete();
                ILeagueFactory addLeagueFactory = leagueConcrete.newAddLeagueFactory();
                league.addLeague(addLeagueFactory);
                int leagueId = league.getId();

                System.out.println("League done....");

                SeasonConcrete seasonConcrete = new SeasonConcrete();
                ISeasonFactory addSeasonDao = seasonConcrete.newAddSeasonFactory();
                season.addSeason(addSeasonDao);
                int seasonId = season.getId();

                System.out.println("Season done....");

                addEvents(league.getId(),league.getNHLRegularSeasonEvents());

                System.out.println("Events done....");

                addGameList(league.getId(),league.getGames().getGameList());

                System.out.println("Game done....");

                addTeamStanding(league.getId(),league.getActiveTeamStanding().getTeamsScoreList());

                System.out.println("Team standing done....");

                if (leagueId != 0 && seasonId != 0) {
                    if (league.getFreeAgent() != null) {
                        int freeAgentId = addFreeAgent(leagueId, seasonId);
                        addPlayerList(0, freeAgentId, seasonId, league.getFreeAgent().getPlayerList());
                    }

                    System.out.println("Free agent done....");

                    if (league.getConferenceList() != null && !league.getConferenceList().isEmpty()) {
                        ConferenceConcrete conferenceConcrete = new ConferenceConcrete();
                        IConferenceFactory addConferenceDao = conferenceConcrete.newAddConferenceFactory();
                        for (Conference conference : league.getConferenceList()) {
                            conference.setLeagueId(leagueId);
                            conference.addConference(addConferenceDao);
                            int conferenceId = conference.getId();

                            System.out.println("Conference done....");

                            for (Division division : conference.getDivisionList()) {
                                DivisionConcrete divisionConcrete = new DivisionConcrete();
                                IDivisionFactory addDivisionDao = divisionConcrete.newAddDivisionFactory();

                                division.setConferenceId(conferenceId);
                                division.addDivision(addDivisionDao);
                                int divisionId = division.getId();

                                System.out.println("Division done....");

                                for (Team team : division.getTeamList()) {
                                    TeamConcrete teamConcrete = new TeamConcrete();
                                    ITeamFactory addTeamDao = teamConcrete.newTeamFactory();

                                    team.setDivisionId(divisionId);
                                    team.addTeam(addTeamDao);
                                    int teamId = team.getId();
                                    addPlayerList(teamId, 0, seasonId, team.getPlayerList());

                                    System.out.println("Player done....");
                                }
                            }

                        }
                    }
                }

            }catch(SQLException sqlException){
                System.out.println("Unable to save the league! Please try again" + sqlException.getMessage());
                System.exit(1);
                sqlException.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addTeamStanding(int id, List<TeamScore> teamScoreList) throws Exception {
        TeamScoreConcrete teamScoreConcrete = new TeamScoreConcrete();
        ITeamScoreFactory addTeamScoreFactory = teamScoreConcrete.newAddTeamScoreFactory();
        for (TeamScore teamScore : teamScoreList) {
            addTeamScoreFactory.addTeamScore(id,0,teamScore);
        }
    }

    private void addEvents(int leagueId,NHLEvents nhlEvents) throws Exception {
        EventConcrete eventConcrete = new EventConcrete();
        IEventFactory addEventFactory = eventConcrete.newAddEventsFactory();
        addEventFactory.addEvent(leagueId,nhlEvents);
    }

    private void addGameList(int leagueId,List<Game> gameList) throws Exception {
        if(gameList != null && !gameList.isEmpty()) {
            GameConcrete gameConcrete = new GameConcrete();
            IGameFactory addGamesFactory = gameConcrete.newAddGamesFactory();
            for (Game game : gameList) {
                addGamesFactory.addGame(leagueId,game);
                System.out.println("Game done...");
            }
        }
    }

    private int addFreeAgent(int leagueId, int seasonId) throws Exception {
        FreeAgentConcrete freeAgentConcrete = new FreeAgentConcrete();
        IFreeAgentFactory freeAgentDao = freeAgentConcrete.newAddFreeAgentFactory();
        FreeAgent freeAgent = league.getFreeAgent();
        freeAgent.setSeasonId(seasonId);
        freeAgent.setLeagueId(leagueId);
        freeAgent.addFreeAgent(freeAgentDao);
        return freeAgent.getId();
    }

    private void addPlayerList(int teamId, int freeAgentId, int seasonId, List<Player> playerList) throws Exception {
        if(playerList != null && !playerList.isEmpty()) {
            PlayerConcrete playerConcrete = new PlayerConcrete();
            IPlayerFactory addPlayerDao = playerConcrete.newAddPlayerFactory();
            for (Player player : playerList) {
                player.setTeamId(teamId);
                player.setFreeAgentId(freeAgentId);
                addPlayerDao.addPlayer(player);
                System.out.println("One Player Done...");
            }
        }
    }

    private Boolean todayIsStartOfSeason(){
        if(league.getCurrentDate().equals(LocalDate.of(LocalDate.now().getYear(),10,01))){
            return true;
        }else{
            return false;
        }
    }

    private ISimulateState exit() {
        if(stanleyCupWinnerDetermined()){
            List<TeamScore> teamScoreList = league.getActiveTeamStanding().getTeamsScoreList();
            if(teamScoreList.get(0).getNumberOfWins() > teamScoreList.get(1).getNumberOfWins()){
                System.out.println(teamScoreList.get(0).getTeamName() +" won the stanley cup!");
            }else{
                System.out.println(teamScoreList.get(1).getTeamName() +" won the stanley cup!");
            }

            return null;
        }else{
            return new AdvanceTimeState(hockeyContext);
        }
    }
    public Boolean stanleyCupWinnerDetermined(){
        NHLEvents nhlEvents = league.getNHLRegularSeasonEvents();
        Games games = league.getGames();
        TeamStanding teamStanding = league.getActiveTeamStanding();
        if(nhlEvents.checkRegularSeasonPassed(league.getCurrentDate()) && games.doGamesDoesNotExistAfterDate(league.getCurrentDate()) && teamStanding.getTeamsScoreList().size() == 2 ){
            return true;
        }
        return false;
    }
}
