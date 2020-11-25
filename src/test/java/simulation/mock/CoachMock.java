package simulation.mock;

import simulation.dao.ICoachDao;
import simulation.model.Coach;
import simulation.model.ICoach;

import java.util.List;

public class CoachMock implements ICoachDao {
    @Override
    public int addCoach(ICoach coach) throws Exception {
        coach = new Coach(1);
        return coach.getId();
    }

    @Override
    public void loadCoachById(int id, ICoach coach) throws Exception {

        switch (id) {
            case 0:
                coach.setId(id);
                coach.setName("Joe Smith");
                coach.setSkating(0.5);
                coach.setShooting(0.8);
                coach.setChecking(0.3);
                coach.setSaving(1.0);
                coach.setLeagueId(1);
                coach.setTeamId(0);
                break;
            case 1:
                coach.setId(id);
                coach.setName("Frank Smith");
                coach.setSkating(0.5);
                coach.setShooting(0.8);
                coach.setChecking(0.3);
                coach.setSaving(0.5);
                coach.setLeagueId(1);
                coach.setTeamId(0);
                break;
            case 2:
                coach.setId(id);
                coach.setName("Sam Smith");
                coach.setSkating(0.6);
                coach.setShooting(0.3);
                coach.setChecking(0.3);
                coach.setSaving(1.0);
                coach.setLeagueId(1);
                coach.setTeamId(0);
                break;
            case 3:
                coach.setId(id);
                coach.setName("Taylor Smith");
                coach.setSkating(0.9);
                coach.setShooting(0.3);
                coach.setChecking(0.5);
                coach.setSaving(0.3);
                coach.setLeagueId(1);
                coach.setTeamId(0);
                break;

            case 4:
                coach.setId(id);
                coach.setName("Who Smith");
                coach.setSkating(0.7);
                coach.setShooting(0.3);
                coach.setChecking(0.5);
                coach.setSaving(0.6);
                coach.setLeagueId(1);
                coach.setTeamId(1);
                break;
            case 5:
                coach.setId(id);
                coach.setName("Samantha Smith");
                coach.setSkating(0.6);
                coach.setShooting(0.4);
                coach.setChecking(0.3);
                coach.setSaving(1.1);
                coach.setLeagueId(1);
                coach.setTeamId(0);
                break;
            case 6:
                coach.setId(id);
                coach.setName("Samantha Smith");
                coach.setSkating(0.2);
                coach.setShooting(0.3);
                coach.setChecking(-0.3);
                coach.setSaving(0.7);
                coach.setLeagueId(1);
                coach.setTeamId(0);
                break;
            case 7:
                coach.setId(id);
                coach.setName("Samantha Smith");
                coach.setSkating(-0.2);
                coach.setShooting(0.3);
                coach.setChecking(0.3);
                coach.setSaving(0.7);
                coach.setLeagueId(1);
                coach.setTeamId(0);
                break;
            case 8:
                coach.setId(id);
                coach.setName("Samantha Smith");
                coach.setSkating(0.2);
                coach.setShooting(1.3);
                coach.setChecking(0.3);
                coach.setSaving(0.7);
                coach.setLeagueId(1);
                coach.setTeamId(0);
                break;
        }
    }

    @Override
    public Coach loadCoachByTeamId(int teamId) throws Exception {
        ICoachDao coachFactory = new CoachMock();
        return new Coach(2, coachFactory);
    }

    @Override
    public List<ICoach> loadFreeCoachListByLeagueId(int leagueId) throws Exception {
        LeagueMock leagueMock = new LeagueMock();
        return leagueMock.formCoachList();
    }
}
