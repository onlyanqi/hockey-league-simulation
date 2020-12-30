package simulation.model;

public class Trophy implements ITrophy {

    String presidentsTrophy;
    String calderMemorialTrophy;
    String vezinaTrophy;
    String jackAdamsAward;
    String mauriceRichardTrophy;
    String robHawkeyMemorialCup;
    String participationAward;

    public Trophy() {
    }

    Trophy(persistance.serializers.ModelsForDeserialization.model.Trophy trophy) {
        this.presidentsTrophy = trophy.presidentsTrophy;
        this.calderMemorialTrophy = trophy.calderMemorialTrophy;
        this.vezinaTrophy = trophy.vezinaTrophy;
        this.jackAdamsAward = trophy.jackAdamsAward;
        this.mauriceRichardTrophy = trophy.mauriceRichardTrophy;
        this.robHawkeyMemorialCup = trophy.robHawkeyMemorialCup;
        this.participationAward = trophy.participationAward;
    }

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
    public String getJackAdamsAward() {
        return jackAdamsAward;
    }

    @Override
    public void setJackAdamsAward(String jackAdamsAward) {
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
