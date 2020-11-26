package simulation.mock;

import simulation.model.ITrophy;


public class TrophyMock {
    public ITrophy loadTrophyById(int id, ITrophy trophy){
        switch (id){
            case 0:
                trophy.setParticipationAward("Simran");
                trophy.setPresidentsTrophy("Mani");
                trophy.setJackAdamsAward("Pratheep");
                trophy.setCalderMemorialTrophy("Anqi");
                trophy.setMauriceRichardTrophy("Nishith");
                trophy.setRobHawkeyMemorialCup("Ram");
                trophy.setVezinaTrophy("Shyam");
                break;
        }
        return trophy;
    }
}
