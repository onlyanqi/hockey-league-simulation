package simulation.state;

import config.AppConfig;
import presentation.ReadUserInput;
import simulation.model.ILeague;
import simulation.model.League;
import persistance.serializers.LeagueDataSerializerDeSerializer;


public class LoadTeamState implements IHockeyState {

    private IHockeyContext hockeyContext;
    private String teamName;
    private ILeague league;
    private ReadUserInput readUserInput;

    public LoadTeamState(IHockeyContext hockeyContext) {
        this.hockeyContext = hockeyContext;
        readUserInput = ReadUserInput.getInstance();
    }

    public ILeague getLeague() {
        return league;
    }

    public void setLeague(ILeague league) {
        this.league = league;
    }

    @Override
    public void entry() throws Exception {
        teamName = readUserInput.getInput("Please enter team name");
        while ((teamName.isEmpty() || teamName == null)) {
            teamName = readUserInput.getInput("Please enter valid and existing team name");
        }
    }


    @Override
    public void process() throws Exception {
        LeagueDataSerializerDeSerializer leagueDataSerializerDeSerializer = AppConfig.getInstance().getDataSerializerDeSerializer();
        String filename = "JsonFiles" + "/" + teamName;

        league = new League(leagueDataSerializerDeSerializer.deSerialize(filename));
        hockeyContext.getUser().setLeague(league);
    }

    @Override
    public IHockeyState exit() {
        PlayerChoiceState playerChoiceState = new PlayerChoiceState(hockeyContext, "How many seasons do you want to simulate", "createOrLoadTeam");
        return playerChoiceState;
    }
}
