package simulation.factory;

import simulation.model.ITeam;
import simulation.model.ITeamStanding;
import simulation.model.TeamStanding;

public class TeamStandingConcrete implements ITeamStandingFactory {
    @Override
    public ITeamStanding newTeamStanding() {
        return new TeamStanding();
    }
}
