package simulation.model;

import simulation.dao.IPlayerDao;

import java.time.LocalDate;
import java.util.List;

public interface IPlayer extends Comparable<IPlayer>{

    boolean isFreeAgent();

    void setIsFreeAgent(boolean freeAgent);

    int getAge();

    void setAge(int age) throws IllegalArgumentException;

    Position getPosition();

    void setPosition(Position position);

    int getTeamId();

    void setTeamId(int teamId);

    int getFreeAgentId();

    void setFreeAgentId(int freeAgentId);

    int getSkating();

    void setSkating(int skating) throws IllegalArgumentException;

    int getShooting();

    void setShooting(int shooting) throws IllegalArgumentException;

    int getChecking();

    void setChecking(int checking) throws IllegalArgumentException;

    int getSaving();

    void setSaving(int saving) throws IllegalArgumentException;

    void setStrength();

    double getStrength();

    boolean isCaptain();

    void setCaptain(boolean captain);

    boolean getInjured();

    void setInjured(boolean isInjured);

    LocalDate getInjuryStartDate();

    void setInjuryStartDate(LocalDate injuryStartDate);

    int getInjuryDatesRange();

    void setInjuryDatesRange(int injuryDatesRange);

    void addPlayer(IPlayerDao addPlayerFactory) throws Exception;

    boolean retirementCheck(ILeague league);

    void injuryCheck(ILeague league);

    void agingInjuryRecovery(ILeague league);

    void setRelativeStrength();

    double getRelativeStrength();

    int getId();

    String getName();

    boolean validName();

    void setName(String name);

    void setId(int id);

    LocalDate getBirthday();

    void setBirthday(LocalDate birthday);

    void calculateAge(ILeague league);

    void findBestReplacement(List<IPlayer> targetPlayerList, List<IPlayer> replacementPlayerList);

    void statDecayCheck(ILeague league);

    boolean isBirthday(ILeague league);

    int getSaves();

    int getPenaltyCount();

    void setSaves(int saves);

    void setPenaltyCount(int penaltyCount);

    int getGoalScore();

    void setGoalScore(int goalScore);
}
