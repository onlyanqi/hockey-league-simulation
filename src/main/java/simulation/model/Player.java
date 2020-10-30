package simulation.model;
import db.data.IPlayerFactory;
import util.DateUtil;
import java.time.LocalDate;
import java.util.Random;


public class Player extends SharedAttributes implements Comparable<Player> {

    private int age;
    private String hometown;
    private Position position;
    private int teamId;
    private int freeAgentId;
    private boolean isCaptain;
    private boolean isInjured;
    private LocalDate injuryStartDate;
    private int injuryDatesRange;
    private int seasonId;
    private boolean isFreeAgent = false;
    private int skating;
    private int shooting;
    private int checking;
    private int saving;
    private double strength;
    private static final String GOALIE = "goalie";

    public Player() {
    }

    public Player(int id) {
        setId(id);
    }

    public Player(int id, IPlayerFactory factory) throws Exception {
        setId(id);
        factory.loadPlayerById(id, this);
    }

    public Player(Player player) {
        if(player == null){
            return;
        }
        this.setId(player.getId());
        this.setName(player.getName());
        this.isFreeAgent = player.isFreeAgent;
        this.setAge(player.getAge());
        this.setPosition(player.getPosition());
        if(player.getPosition().toString().equals(GOALIE)){
            this.setSaving(player.getSaving());
        }
        this.setChecking(player.getChecking());
        this.setShooting(player.getShooting());
        this.setSkating(player.getSkating());
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

    public void setAge(int age) {
        this.age = age;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        if(isNotEmpty(hometown)){
            this.hometown = hometown;
        }
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

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
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

    public void setSkating(int skating) {
        this.skating = skating;
    }

    public int getShooting() {
        return shooting;
    }

    public void setShooting(int shooting) {
        this.shooting = shooting;
    }

    public int getChecking() {
        return checking;
    }

    public void setChecking(int checking) {
        this.checking = checking;
    }

    public int getSaving() {
        return saving;
    }

    public void setSaving(int saving) {
        if(this.position.toString().equals(GOALIE)){
            this.saving = saving;
        }
    }

    public void setStrength() {
        switch (position) {
            case forward:
                this.strength = this.getSkating() + this.getShooting() + (double) (this.getChecking() / 2);
                break;
            case defense:
                this.strength = this.getSkating() + this.getChecking() + (double) (this.getShooting() / 2);
                break;
            case goalie:
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
        if(addPlayerFactory ==  null){
            return;
        }
        addPlayerFactory.addPlayer(this);
    }

    public boolean retirementCheck(Aging aging) {
        if(aging == null){
            return false;
        }
        double increaseRate = 0.5 / (aging.getMaximumAge() - aging.getAverageRetirementAge());
        if (this.age < aging.getAverageRetirementAge()) {
            Random randomRetire1 = new Random();
            double chance1 = (aging.getAverageRetirementAge() - this.age) * (1 - increaseRate) * 0.5;
            return randomRetire1.nextDouble() < chance1;
        } else if (this.age < aging.getMaximumAge()) {
            Random randomRetire2 = new Random();
            double chance2 = (this.age - aging.getAverageRetirementAge()) * (1 + increaseRate) * 0.5;
            return randomRetire2.nextDouble() < chance2;
        } else return this.age >= aging.getMaximumAge();
    }

    public void getOlder() {
        this.age++;
    }

    public void injuryCheck(League league) {
        if(league == null){
            return;
        }
        Random randomInjuryChance = new Random();
        double chanceOfInjury = randomInjuryChance.nextDouble();
        if (!this.getInjured() && chanceOfInjury < league.getGamePlayConfig().getInjury().getRandomInjuryChance()) {
            this.setInjuryStartDate(league.getCurrentDate());
            Random randomInjuryDays = new Random();
            int injuryDaysHigh = league.getGamePlayConfig().getInjury().getInjuryDaysHigh();
            int injuryDaysLow = league.getGamePlayConfig().getInjury().getInjuryDaysLow();
            this.setInjuryDatesRange(randomInjuryDays.nextInt(injuryDaysHigh - injuryDaysLow) + injuryDaysLow);
            this.setInjured(true);
        }
    }

    public void agingInjuryRecovery(League league) {
        if(league == null){
            return;
        }
        if (this.getInjured() && DateUtil.diffDays(this.getInjuryStartDate(), league.getCurrentDate()) >= this.getInjuryDatesRange()) {
            this.setInjured(false);
            this.setInjuryStartDate(null);
        }
    }

    public enum Position {
        forward,
        defense,
        goalie
    }

    @Override
    public int compareTo(Player player) {
        if(player == null){
            return -2;
        }
        double compare = this.getStrength()-player.getStrength();
        int returnValue = 0;
        if(compare > 0){
            returnValue = 1;
        } else if (compare < 0){
            returnValue = -1;
        }
        return returnValue;
    }
}
