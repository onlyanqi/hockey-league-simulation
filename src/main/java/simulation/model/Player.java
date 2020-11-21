package simulation.model;

import db.data.IPlayerFactory;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Player extends SharedAttributes implements Comparable<Player> {

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
    private boolean isRetired = false;
    private int skating;
    private int shooting;
    private int checking;
    private int saving;
    private double strength;
    private double relativeStrength;

    public Player() {
        setId(System.identityHashCode(this));
    }

    public Player(int id) {
        setId(id);
    }

    public Player(int id, IPlayerFactory factory) throws Exception {
        setId(id);
        factory.loadPlayerById(id, this);
    }

    public Player(Player player) {
        if (player == null) {
            return;
        }
        this.setId(player.getId());
        this.setName(player.getName());
        this.isFreeAgent = player.isFreeAgent;
        this.setAge(player.getAge());
        this.setPosition(player.getPosition());
        this.setSaving(player.getSaving());
        this.setChecking(player.getChecking());
        this.setShooting(player.getShooting());
        this.setSkating(player.getSkating());
        this.setStrength();
        this.setRelativeStrength();
    }

    public enum Position {
        FORWARD {
            public String toString() {
                return "forward";
            }
        },
        DEFENSE {
            public String toString() {
                return "defense";
            }
        },
        GOALIE {
            public String toString() {
                return "goalie";
            }
        }
    }

    public boolean isFreeAgent() {
        return isFreeAgent;
    }

    public void setIsFreeAgent(boolean freeAgent) {
        isFreeAgent = freeAgent;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws IllegalArgumentException {
        if (age < 0) {
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

    public void setPosition(Position position) {
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
            throw new IllegalArgumentException("Player checking is out of range (1-20)");
        }
        this.checking = checking;
    }

    public int getSaving() {
        return saving;
    }

    public void setSaving(int saving) throws IllegalArgumentException {
        if (saving < 1 || saving > 20) {
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

    public void addPlayer(IPlayerFactory addPlayerFactory) throws Exception {
        if (addPlayerFactory == null) {
            return;
        }
        addPlayerFactory.addPlayer(this);
    }

    public boolean retirementCheck(IAging aging) {
        //public boolean retirementCheck(Aging aging) {
        if (aging == null) {
            return false;
        }
        double increaseRate = 0.5 / (aging.getMaximumAge() - aging.getAverageRetirementAge());
        if (this.age < aging.getAverageRetirementAge()) {
            Random randomRetire1 = new Random();
            double chance1 = 0.5 - ((aging.getAverageRetirementAge() - this.age) * increaseRate);
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

    public void calculateAge(League league) {
        LocalDate birthday = this.getBirthday();
        LocalDate currentDate = league.getCurrentDate();
        if (birthday == null || currentDate == null) {
            return;
        }
        int age = Period.between(birthday,currentDate).getYears();
        this.setAge(age);
    }

    public void injuryCheck(League league) {
        if (league == null) {
            return;
        }
        Random randomInjuryChance = new Random();
        double chanceOfInjury = randomInjuryChance.nextDouble();
        if (this.getInjured()) {
            return;
        }
        if (chanceOfInjury < league.getGamePlayConfig().getInjury().getRandomInjuryChance()) {
            this.setInjuryStartDate(league.getCurrentDate());
            Random randomInjuryDays = new Random();
            int injuryDaysHigh = league.getGamePlayConfig().getInjury().getInjuryDaysHigh();
            int injuryDaysLow = league.getGamePlayConfig().getInjury().getInjuryDaysLow();
            this.setInjuryDatesRange(randomInjuryDays.nextInt(injuryDaysHigh - injuryDaysLow) + injuryDaysLow);
            this.setInjured(true);

            // Swap injured player to inactive
        }
    }

    public void findBestReplacement(List<Player> targetPlayerList, Position position, int index, List<Player> replacementPlayerList) {
        Collections.sort(replacementPlayerList, Collections.reverseOrder());
        Player replacePlayer = new Player();
        int size = replacementPlayerList.size();
        for (int i = 0; i < size; i++) {
            if (replacementPlayerList.get(i).getPosition().equals(position)) {
                replacementPlayerList.get(i).setTeamId(targetPlayerList.get(index).getTeamId());
                replacePlayer = new Player(replacementPlayerList.get(i));
                replacementPlayerList.remove(i);
                break;
            }
        }
        if(replacePlayer.getName() == null){
            return;
        }
        targetPlayerList.add(replacePlayer);
        targetPlayerList.remove(index);
    }

    public void agingInjuryRecovery(League league) {
        if (league == null) {
            return;
        }
        if (this.getInjured() && DateTime.diffDays(this.getInjuryStartDate(), league.getCurrentDate()) >= this.getInjuryDatesRange()) {
            this.setInjured(false);
            this.setInjuryStartDate(null);
            this.setInjuryDatesRange(0);
        }
    }

    public boolean isRetired() {
        return isRetired;
    }

    public void setRetired(boolean retired) {
        this.isRetired = retired;
    }

    @Override
    public int compareTo(Player player) {
        if (player == null) {
            return -2;
        }
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
