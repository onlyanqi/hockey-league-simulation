package simulation.model;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import persistance.dao.IPlayerDao;
import presentation.ConsoleOutput;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Player extends SharedAttributes implements IPlayer {

    private static Logger log = Logger.getLogger(Player.class);
    private int age;
    private LocalDate birthday;
    private Position position;
    private int teamId;
    private int freeAgentId;
    private boolean isCaptain;
    private boolean isInjured;
    private LocalDate injuryStartDate;
    private int injuryDatesRange;
    private boolean isFreeAgent = false;
    private int skating;
    private int shooting;
    private int checking;
    private int saving;
    private double strength;
    private double relativeStrength;
    private int penaltyCount;
    private int goalScore;
    private int saves;

    public Player() {
        setId(System.identityHashCode(this));
    }

    public Player(int id) {
        setId(id);
    }

    public Player(int id, IPlayerDao factory) throws Exception {
        setId(id);
        factory.loadPlayerById(id, this);
    }

    public Player(IPlayer player) {
        if (player == null) {
            return;
        }
        this.setId(player.getId());
        this.setName(player.getName());
        this.isFreeAgent = player.isFreeAgent();
        this.setFreeAgentId(player.getFreeAgentId());
        this.setAge(player.getAge());
        this.setBirthday(player.getBirthday());
        this.setTeamId(player.getTeamId());
        this.setInjured(player.getInjured());
        this.setInjuryDatesRange(player.getInjuryDatesRange());
        this.setInjuryStartDate(player.getInjuryStartDate());
        this.setCaptain(player.isCaptain());
        this.setPosition(player.getPosition());
        this.setSaving(player.getSaving());
        this.setChecking(player.getChecking());
        this.setShooting(player.getShooting());
        this.setSkating(player.getSkating());
        this.setStrength();
        this.setRelativeStrength();
        this.setSaves(player.getSaves());
        this.setPenaltyCount(player.getPenaltyCount());
        this.setGoalScore(player.getGoalScore());
    }

    public Player(persistance.serializers.ModelsForDeserialization.model.Player playerFromDeserialization) {
        this.setId(playerFromDeserialization.id);
        this.setName(playerFromDeserialization.name);
        this.age = playerFromDeserialization.age;
        this.birthday = playerFromDeserialization.birthday;
        this.checking = playerFromDeserialization.checking;
        this.freeAgentId = playerFromDeserialization.freeAgentId;
        this.goalScore = playerFromDeserialization.goalScore;
        this.injuryDatesRange = playerFromDeserialization.injuryDatesRange;
        this.injuryStartDate = playerFromDeserialization.injuryStartDate;
        this.isCaptain = playerFromDeserialization.isCaptain;
        this.isInjured = playerFromDeserialization.isInjured;
        this.penaltyCount = playerFromDeserialization.penaltyCount;
        this.position = playerFromDeserialization.position;
        this.relativeStrength = playerFromDeserialization.relativeStrength;
        this.saves = playerFromDeserialization.saves;
        this.saving = playerFromDeserialization.saving;
        this.shooting = playerFromDeserialization.shooting;
        this.skating = playerFromDeserialization.skating;
        this.strength = playerFromDeserialization.strength;
        this.isFreeAgent = playerFromDeserialization.isFreeAgent;
    }

    public boolean isFreeAgent() {
        return isFreeAgent;
    }

    public int getGoalScore() {
        return goalScore;
    }

    public void setGoalScore(int goalScore) {
        this.goalScore = goalScore;
    }

    public int getPenaltyCount() {
        return penaltyCount;
    }

    public void setPenaltyCount(int penaltyCount) {
        this.penaltyCount = penaltyCount;
    }

    public int getSaves() {
        return saves;
    }

    public void setSaves(int saves) {
        this.saves = saves;
    }

    public void setIsFreeAgent(boolean freeAgent) {
        isFreeAgent = freeAgent;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws IllegalArgumentException {
        if (age < 0) {
            log.error("Player age less than 0");
            throw new IllegalArgumentException("Player age must greater than 0!");
        }
        this.age = age;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(simulation.model.Position position) {
        this.position = position;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getFreeAgentId() {
        return freeAgentId;
    }

    public void setFreeAgentId(int freeAgentId) {
        this.freeAgentId = freeAgentId;
    }

    public int getSkating() {
        return skating;
    }

    public void setSkating(int skating) throws IllegalArgumentException {
        if (skating < 1 || skating > 20) {
            throw new IllegalArgumentException("Player skating is out of range (1-20)");
        }
        this.skating = skating;
    }

    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) throws IllegalArgumentException {
        if (shooting < 1 || shooting > 20) {
            throw new IllegalArgumentException("Player shooting is out of range (1-20)");
        }
        this.shooting = shooting;
    }

    public int getChecking() {
        return checking;
    }

    public void setChecking(int checking) throws IllegalArgumentException {
        if (checking < 1 || checking > 20) {
            log.error("Player checking is out of range (1-20)");
            throw new IllegalArgumentException("Player checking is out of range (1-20)");
        }
        this.checking = checking;
    }

    public int getSaving() {
        return saving;
    }

    public void setSaving(int saving) throws IllegalArgumentException {
        if (saving < 1 || saving > 20) {
            log.error("Player saving is out of range (1-20)");
            throw new IllegalArgumentException("Player saving is out of range (1-20)");
        } else if (this.position == Position.GOALIE) {
            this.saving = saving;
        } else {
            this.saving = 1;
        }
    }

    public void setStrength() {
        switch (position) {
            case FORWARD:
                this.strength = this.getSkating() + this.getShooting() + (double) (this.getChecking() / 2);
                break;
            case DEFENSE:
                this.strength = this.getSkating() + this.getChecking() + (double) (this.getShooting() / 2);
                break;
            case GOALIE:
                this.strength = this.getSkating() + this.getSaving();
                break;
        }
    }

    public double getStrength() {
        return strength;
    }

    public boolean isCaptain() {
        return isCaptain;
    }

    public void setCaptain(boolean captain) {
        isCaptain = captain;
    }

    public boolean getInjured() {
        return isInjured;
    }

    public void setInjured(boolean isInjured) {
        this.isInjured = isInjured;
    }

    public LocalDate getInjuryStartDate() {
        return this.injuryStartDate;
    }

    public void setInjuryStartDate(LocalDate injuryStartDate) {
        this.injuryStartDate = injuryStartDate;
    }

    public int getInjuryDatesRange() {
        return this.injuryDatesRange;
    }

    public void setInjuryDatesRange(int injuryDatesRange) {
        this.injuryDatesRange = injuryDatesRange;
    }

    @Override
    public void addPlayer(IPlayerDao addPlayerFactory) throws Exception {
        if (addPlayerFactory == null) {
            return;
        }
        addPlayerFactory.addPlayer(this);
    }

    @Override
    public boolean retirementCheck(ILeague league) {
        if (league == null) {
            log.debug("Unable to do retirement check, league is null");
            return false;
        }
        IAging aging = league.getGamePlayConfig().getAging();
        this.calculateAge(league);
        double increaseRate = 0.5 / (aging.getMaximumAge() - aging.getAverageRetirementAge());
        if (this.age < aging.getAverageRetirementAge()) {
            Random randomRetire1 = new Random();
            double chance1 = 0.5 - ((aging.getAverageRetirementAge() - this.age) * increaseRate * 1.5);
            if (chance1 < 0.0) {
                chance1 = 0.0;
            }
            return randomRetire1.nextDouble() < chance1;
        } else if (this.age < aging.getMaximumAge()) {
            Random randomRetire2 = new Random();
            double chance2 = (this.age - aging.getAverageRetirementAge()) * increaseRate + 0.5;
            if (chance2 > 1.0) {
                chance2 = 1.0;
            }
            return randomRetire2.nextDouble() < chance2;
        } else return this.age >= aging.getMaximumAge();
    }

    @Override
    public void calculateAge(ILeague league) {
        LocalDate birthday = this.getBirthday();
        LocalDate currentDate = league.getCurrentDate();
        if (birthday == null || currentDate == null) {
            log.debug("Unable to calculate age, player's birthday is null or current date is null");
            return;
        }
        int age = Period.between(birthday, currentDate).getYears();
        this.setAge(age);
    }

    @Override
    public boolean isBirthday(ILeague league) {
        LocalDate currentDate = league.getCurrentDate();
        int currentDayOfMonth = currentDate.getDayOfMonth();
        int currentMonth = currentDate.getMonthValue();
        int birthDayOfMonth = birthday.getDayOfMonth();
        int birthMonth = birthday.getMonthValue();

        return currentDayOfMonth == birthDayOfMonth && currentMonth == birthMonth;
    }

    @Override
    public void statDecayCheck(ILeague league) {
        if (isBirthday(league)) {
            calculateAge(league);
            Random randomStatDecay = new Random();
            double chanceOfStatDecay = randomStatDecay.nextDouble();
            double statDecayChance = league.getGamePlayConfig().getAging().getStatDecayChance();
            if (chanceOfStatDecay < statDecayChance) {
                this.setSkating(Math.max((this.getSkating() - 1), 1));
                this.setShooting(Math.max((this.getShooting() - 1), 1));
                this.setChecking(Math.max((this.getChecking() - 1), 1));
                this.setSaving(Math.max((this.getSaving() - 1), 1));
            }
        }
    }

    @Override
    public void injuryCheck(ILeague league) {
        if (league == null) {
            log.debug("Unable to do injury check, league is null");
            return;
        }
        Random randomInjuryChance = new Random();
        double chanceOfInjury = randomInjuryChance.nextDouble();
        if (this.getInjured()) {
            log.debug("No need for injury check, this player is already injured");
            return;
        }
        if (chanceOfInjury < league.getGamePlayConfig().getInjury().getRandomInjuryChance()) {
            this.setInjuryStartDate(league.getCurrentDate());
            Random randomInjuryDays = new Random();
            int injuryDaysHigh = league.getGamePlayConfig().getInjury().getInjuryDaysHigh();
            int injuryDaysLow = league.getGamePlayConfig().getInjury().getInjuryDaysLow();
            int range = injuryDaysHigh - injuryDaysLow + 1;
            int injuryDatesRange = randomInjuryDays.nextInt(range) + injuryDaysLow;
            this.setInjuryDatesRange(injuryDatesRange);
            this.setInjured(true);
            log.debug("Play get injured on " + league.getCurrentDate() + " for " + injuryDatesRange + " days.");
            ConsoleOutput.getInstance().printMsgToConsole("Play get injured on " + league.getCurrentDate() + " for " + injuryDatesRange + " days.");
        }
    }

    @Override
    public void agingInjuryRecovery(ILeague league) {
        if (league == null) {
            log.debug("Unable to aging injury recovery, league is null");
            return;
        }
        LocalDate injuryDate = this.getInjuryStartDate();
        LocalDate currentDate = league.getCurrentDate();
        if (this.getInjured() && DateTime.diffDays(injuryDate, currentDate) >= this.getInjuryDatesRange()) {
            log.debug("Play recovered on " + currentDate + ", got injured on " + injuryDate);
            ConsoleOutput.getInstance().printMsgToConsole("Play finally recovered on " + currentDate + " after " + this.getInjuryDatesRange() + " days of injury");
            this.setInjured(false);
            this.setInjuryStartDate(null);
            this.setInjuryDatesRange(0);
        }
    }

    @Override
    public void findBestReplacement(List<IPlayer> targetPlayerList, List<IPlayer> replacementPlayerList) {
        Collections.sort(replacementPlayerList, Collections.reverseOrder());
        Player replacePlayer = new Player();
        Position position = this.getPosition();
        for (IPlayer player : replacementPlayerList) {
            if (player.getPosition().equals(position)) {
                replacePlayer = new Player(player);
                replacementPlayerList.remove(player);
                break;
            }
        }
        if (replacePlayer.getName() == null) {
            return;
        }
        replacePlayer.setTeamId(this.getTeamId());
        targetPlayerList.add(replacePlayer);
        targetPlayerList.remove(this);
    }

    @Override
    public int compareTo(@NotNull IPlayer player) {
        double compare = this.getStrength() - player.getStrength();
        int returnValue = 0;
        if (compare > 0) {
            returnValue = 1;
        } else if (compare < 0) {
            returnValue = -1;
        }
        return returnValue;
    }

    public void setRelativeStrength() {
        switch (position) {
            case FORWARD:
                this.relativeStrength = this.strength / 5;
                break;
            case DEFENSE:
                this.relativeStrength = this.strength / 5;
                break;
            case GOALIE:
                this.relativeStrength = this.strength / 4;
                break;
        }
    }

    public double getRelativeStrength() {
        return this.relativeStrength;
    }
}
