package simulation.model;

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

    String getName();

    void setName(String name);

    int getId();

    void setId(int id);

    int getCoachingEffectiveness();

    void setCoachingEffectiveness(int coachingEffectiveness);
}
