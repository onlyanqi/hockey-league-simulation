package simulation.model;

public class Trophy implements ITrophy {

    String presidentsTrophy;
    String calderMemorialTrophy;
    String vezinaTrophy;
    ICoach jackAdamsAward;
    String mauriceRichardTrophy;
    String robHawkeyMemorialCup;
    String participationAward;

    @Override
    public String getPresidentsTrophy() {
        return presidentsTrophy;
    }

    @Override
    public void setPresidentsTrophy(String presidentsTrophy) {
        this.presidentsTrophy = presidentsTrophy;
    }

    @Override
    public String getCalderMemorialTrophy() {
        return calderMemorialTrophy;
    }

    @Override
    public void setCalderMemorialTrophy(String calderMemorialTrophy) {
        this.calderMemorialTrophy = calderMemorialTrophy;
    }

    @Override
    public String getVezinaTrophy() {
        return vezinaTrophy;
    }

    @Override
    public void setVezinaTrophy(String vezinaTrophy) {
        this.vezinaTrophy = vezinaTrophy;
    }

    @Override
    public ICoach getJackAdamsAward() {
        return jackAdamsAward;
    }

    @Override
    public void setJackAdamsAward(ICoach jackAdamsAward) {
        this.jackAdamsAward = jackAdamsAward;
    }

    @Override
    public String getMauriceRichardTrophy() {
        return mauriceRichardTrophy;
    }

    @Override
    public void setMauriceRichardTrophy(String mauriceRichardTrophy) {
        this.mauriceRichardTrophy = mauriceRichardTrophy;
    }

    @Override
    public String getRobHawkeyMemorialCup() {
        return robHawkeyMemorialCup;
    }

    @Override
    public void setRobHawkeyMemorialCup(String robHawkeyMemorialCup) {
        this.robHawkeyMemorialCup = robHawkeyMemorialCup;
    }

    @Override
    public String getParticipationAward() {
        return participationAward;
    }

    @Override
    public void setParticipationAward(String participationAward) {
        this.participationAward = participationAward;
    }
}
