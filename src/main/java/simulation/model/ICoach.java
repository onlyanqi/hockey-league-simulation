package simulation.model;

import db.data.ICoachDao;

public interface ICoach {

    int getTeamId();

    void setTeamId(int teamId);

    int getLeagueId();

    void setLeagueId(int leagueId);

    Double getSkating();

    void setSkating(Double skating);

    Double getShooting();

    void setShooting(Double shooting);

    Double getChecking();

    void setChecking(Double checking);

    Double getSaving();

    void setSaving(Double saving);

    void setName(String name);

    String getName();

    int getId();

    void setId(int id);

    int getCoachingEffectiveness();

    void setCoachingEffectiveness(int coachingEffectiveness);
}
