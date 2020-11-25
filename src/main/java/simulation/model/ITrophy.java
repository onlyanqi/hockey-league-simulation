package simulation.model;

public interface ITrophy {
    String getPresidentsTrophy();

    void setPresidentsTrophy(String presidentsTrophy);

    String getCalderMemorialTrophy();

    void setCalderMemorialTrophy(String calderMemorialTrophy);

    String getVezinaTrophy();

    void setVezinaTrophy(String vezinaTrophy);

    ICoach getJackAdamsAward();

    void setJackAdamsAward(ICoach jackAdamsAward);

    String getMauriceRichardTrophy();

    void setMauriceRichardTrophy(String mauriceRichardTrophy);

    String getRobHawkeyMemorialCup();

    void setRobHawkeyMemorialCup(String robHawkeyMemorialCup);

    String getParticipationAward();

    void setParticipationAward(String participationAward);
}
