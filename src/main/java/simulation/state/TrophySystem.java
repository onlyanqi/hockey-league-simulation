package simulation.state;

import presentation.ConsoleOutput;
import simulation.model.*;

import java.util.ArrayList;
import java.util.List;

public class TrophySystem implements IHockeyState{
    private IHockeyContext hockeyContext;
    private ILeague league;
    private ConsoleOutput consoleOutput;
    private ITrophy trophy;


    TrophySystem(IHockeyContext hockeyContext){
        this.hockeyContext = hockeyContext;
        league = hockeyContext.getUser().getLeague();
        consoleOutput = ConsoleOutput.getInstance();
    }

    @Override
    public void entry() throws Exception {
        List<ITeamScore> teamScores= getSortedTeamScores();
        trophy = league.getTrophy();
        trophy.setPresidentsTrophy(teamScores.get(teamScores.size()-1).getName());
        trophy.setParticipationAward(teamScores.get(0).getName());
        List<IPlayer> playerList = league.createPlayerList();
        List<ICoach> coachList = league.createCoachList();
        trophy.setJackAdamsAward(calculateJackAdamsAward(coachList));
    }

    List<ITeamScore> getSortedTeamScores(){
        List<ITeam> teamList = league.createTeamList();
        ITeamStanding teamStanding= league.getRegularSeasonStanding();
        List<ITeamScore> teamScores = teamStanding.getTeamsRankAcrossLeague(league);
        return teamScores;
    }

    ICoach calculateJackAdamsAward(List<ICoach> coachList){
        ICoach winnerCoach = coachList.get(0);
        for (ICoach coach : coachList) {
            if(coach.getCoachingEffectiveness()>winnerCoach.getCoachingEffectiveness()){
                winnerCoach=coach;
            }
        }
        return winnerCoach;
    }


    @Override
    public void process() throws Exception {

    }

    @Override
    public IHockeyState exit() {
        return null;
    }
}
