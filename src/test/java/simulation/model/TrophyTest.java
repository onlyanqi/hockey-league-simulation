package simulation.model;

import org.junit.Test;
import simulation.mock.TrophyMock;

import static org.junit.Assert.assertEquals;


public class TrophyTest {

    @Test
    public void getPresidentsTrophyTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        String award = trophy.getPresidentsTrophy();
        assertEquals(award, "Mani");
    }

    @Test
    public void setPresidentsTrophyTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        trophy.setPresidentsTrophy("Mishi");
        String award = trophy.getPresidentsTrophy();
        assertEquals(award, "Mishi");
    }

    @Test
    public void getCalderMemorialTrophyTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        String award = trophy.getCalderMemorialTrophy();
        assertEquals(award, "Anqi");
    }

    @Test
    public void setCalderMemorialTrophyTes() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        trophy.setCalderMemorialTrophy("Mishi");
        String award = trophy.getCalderMemorialTrophy();
        assertEquals(award, "Mishi");
    }

    @Test
    public void getVezinaTrophyTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        String award = trophy.getVezinaTrophy();
        assertEquals(award, "Shyam");
    }

    @Test
    public void setVezinaTrophyTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        trophy.setVezinaTrophy("Mishi");
        String award = trophy.getVezinaTrophy();
        assertEquals(award, "Mishi");
    }

    @Test
    public void getJackAdamsAwardTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        String award = trophy.getJackAdamsAward();
        assertEquals(award, "Pratheep");
    }

    @Test
    public void setJackAdamsAwardTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        trophy.setJackAdamsAward("Mishi");
        String award = trophy.getJackAdamsAward();
        assertEquals(award, "Mishi");
    }

    @Test
    public void getMauriceRichardTrophyTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        String award = trophy.getMauriceRichardTrophy();
        assertEquals(award, "Nishith");
    }

    @Test
    public void setMauriceRichardTrophyTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        trophy.setMauriceRichardTrophy("Mishi");
        String award = trophy.getMauriceRichardTrophy();
        assertEquals(award, "Mishi");
    }

    @Test
    public void getRobHawkeyMemorialCupTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        String award = trophy.getRobHawkeyMemorialCup();
        assertEquals(award, "Ram");
    }

    @Test
    public void setRobHawkeyMemorialCupTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        trophy.setRobHawkeyMemorialCup("Mishi");
        String award = trophy.getRobHawkeyMemorialCup();
        assertEquals(award, "Mishi");
    }

    @Test
    public void getParticipationAwardTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        String award = trophy.getParticipationAward();
        assertEquals(award, "Simran");
    }

    @Test
    public void setParticipationAwardTest() {
        ITrophy trophy = new TrophyMock().loadTrophyById(0, new Trophy());
        trophy.setParticipationAward("Mishi");
        String award = trophy.getParticipationAward();
        assertEquals(award, "Mishi");
    }

}
