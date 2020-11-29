package simulation.model;

import simulation.dao.IAgingDao;

import java.time.LocalDate;
import java.util.List;

public class Aging extends SharedAttributes implements IAging {

    private int averageRetirementAge;

    private int maximumAge;

    private int leagueId;

    private Double statDecayChance;

    public Aging() {
        setId(System.identityHashCode(this));
    }

    public Aging(int id) {
        setId(id);
    }

    public Aging(int id, IAgingDao loadAgingFactory) throws Exception {
        setId(id);
        loadAgingFactory.loadAgingById(id, this);
    }

    public Aging(simulation.serializers.ModelsForDeserialization.model.Aging agingFromDeserialization) {
        this.averageRetirementAge = agingFromDeserialization.averageRetirementAge;
        this.maximumAge = agingFromDeserialization.maximumAge;
        this.leagueId = agingFromDeserialization.leagueId;
        this.statDecayChance = agingFromDeserialization.statDecayChance;
        this.setName(agingFromDeserialization.name);
        this.setId(agingFromDeserialization.id);
    }

    @Override
    public int getLeagueId() {
        return leagueId;
    }

    @Override
    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public int getAverageRetirementAge() {
        return averageRetirementAge;
    }

    @Override
    public void setAverageRetirementAge(int averageRetirementAge) throws IllegalArgumentException {
        if (averageRetirementAge < 0) {
            throw new IllegalArgumentException("averageRetirementAge must be greater than 0!");
        }
        this.averageRetirementAge = averageRetirementAge;
    }

    @Override
    public int getMaximumAge() {
        return maximumAge;
    }

    @Override
    public void setMaximumAge(int maximumAge) throws IllegalArgumentException {
        if (maximumAge < 0) {
            throw new IllegalArgumentException("maximumAge must be greater than 0!");
        }
        if (this.getAverageRetirementAge() >= maximumAge) {
            throw new IllegalArgumentException("Maximum retirement age must be greater than average retirement age!");
        }
        this.maximumAge = maximumAge;
    }

    @Override
    public Double getStatDecayChance() {
        return statDecayChance;
    }

    @Override
    public void setStatDecayChance(Double statDecayChance) {
        this.statDecayChance = statDecayChance;
    }

    @Override
    public void agingPlayerDay(ILeague league) {
        List<IConference> conferenceList = league.getConferenceList();
        List<IPlayer> freeAgentList = league.getFreeAgent().getPlayerList();
        for (IConference conference : conferenceList) {
            List<IDivision> divisionList = conference.getDivisionList();
            for (IDivision division : divisionList) {
                List<ITeam> teamList = division.getTeamList();
                for (ITeam team : teamList) {
                    team.setActivePlayerList();
                    List<IPlayer> playerList = team.getPlayerList();
                    for (IPlayer teamPlayer : playerList) {
                        teamPlayer.statDecayCheck(league);
                        teamPlayer.agingInjuryRecovery(league);
                    }
                }
            }
        }
        for (IPlayer freeAgentPlayer : freeAgentList) {
            freeAgentPlayer.statDecayCheck(league);
            freeAgentPlayer.agingInjuryRecovery(league);
        }
    }

    @Override
    public void agingPlayerPeriod(ILeague league, LocalDate before) {
        LocalDate currentDate = league.getCurrentDate();
        LocalDate agingDate = before;
        while (currentDate.compareTo(agingDate) > 0) {
            agingPlayerDay(league);
            agingPlayerRetirement(league);
            agingDate = agingDate.plusDays(1);
        }
    }

    @Override
    public void agingPlayerRetirement(ILeague league) {
        List<IConference> conferenceList = league.getConferenceList();
        List<IPlayer> freeAgentList = league.getFreeAgent().getPlayerList();
        List<IPlayer> retiredPlayerList = league.getRetiredPlayerList();

        for (IConference conference : conferenceList) {
            List<IDivision> divisionList = conference.getDivisionList();
            for (IDivision division : divisionList) {
                List<ITeam> teamList = division.getTeamList();
                for (ITeam team : teamList) {
                    List<IPlayer> playerList = team.getPlayerList();
                    int size = playerList.size();
                    for (int i = size - 1; i >= 0; i--) {
                        IPlayer teamPlayer = playerList.get(i);
                        teamPlayer.calculateAge(league);
                        if (teamPlayer.isBirthday(league)) {
                            if (teamPlayer.retirementCheck(league)) {
                                retiredPlayerList.add(teamPlayer);
                                playerList.remove(teamPlayer);
                                teamPlayer.findBestReplacement(playerList, freeAgentList);
                            }
                        }
                    }
                }
            }
        }
        int size = freeAgentList.size();
        for (int i = size - 1; i >= 0; i--) {
            IPlayer freeAgentPlayer = freeAgentList.get(i);
            freeAgentPlayer.calculateAge(league);
            if (freeAgentPlayer.isBirthday(league)) {
                if (freeAgentPlayer.retirementCheck(league)) {
                    retiredPlayerList.add(freeAgentPlayer);
                    freeAgentList.remove(i);
                }
            }
        }
    }
}
