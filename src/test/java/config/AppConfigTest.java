package config;

import org.junit.Test;
import presentation.ConsoleOutputForTeamCreation;
import presentation.UseInputForTeamCreation;
import simulation.factory.LeagueConcrete;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class AppConfigTest {
    @Test
    public void getInstanceTest() {
        assertTrue(AppConfig.getInstance() instanceof AppConfig);
        assertNotEquals(AppConfig.getInstance() instanceof AppConfig, false);
    }


    @Test
    public void getInputForTeamCreationTest() {
        AppConfig appConfig = AppConfig.getInstance();
        assertTrue(appConfig.getInputForTeamCreation() instanceof UseInputForTeamCreation);
        assertNotEquals(appConfig.getInputForTeamCreation() instanceof UseInputForTeamCreation, false);
    }

    @Test
    public void getOutputForTeamCreationTest() {
        AppConfig appConfig = AppConfig.getInstance();
        assertTrue(appConfig.getOutputForTeamCreation() instanceof ConsoleOutputForTeamCreation);
        assertNotEquals(appConfig.getOutputForTeamCreation() instanceof ConsoleOutputForTeamCreation, false);
    }

    @Test
    public void getLeagueConcreteTest() {
        AppConfig appConfig = AppConfig.getInstance();
        assertTrue(appConfig.getLeagueConcrete() instanceof LeagueConcrete);
        assertNotEquals(appConfig.getLeagueConcrete(), null);
    }
}
