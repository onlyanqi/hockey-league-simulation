package config;

import org.junit.Test;
import presentation.ConsoleOutputForTeamCreation;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import presentation.UseInputForTeamCreation;
import simulation.App;
import simulation.factory.LeagueConcrete;
import simulation.serializers.LeagueDataSerializerDeSerializer;

import static org.junit.Assert.*;

public class AppConfigTest {
    private static AppConfig appConfig = null;

    private static IUserInputForTeamCreation inputForTeamCreation;
    private static IConsoleOutputForTeamCreation outputForTeamCreation;
    private static LeagueDataSerializerDeSerializer dataSerializer;
    private static LeagueConcrete leagueConcrete;


    @Test
    public void getInstanceTest(){
        assertTrue(AppConfig.getInstance() instanceof AppConfig);
        assertNotEquals(AppConfig.getInstance() instanceof AppConfig,false);
    }


    @Test
    public void getInputForTeamCreationTest(){
        AppConfig appConfig = AppConfig.getInstance();
        assertTrue(appConfig.getInputForTeamCreation() instanceof UseInputForTeamCreation);
        assertNotEquals(appConfig.getInputForTeamCreation() instanceof UseInputForTeamCreation,false);
    }

    @Test
    public void getOutputForTeamCreationTest(){
        AppConfig appConfig = AppConfig.getInstance();
        assertTrue(appConfig.getOutputForTeamCreation() instanceof ConsoleOutputForTeamCreation);
        assertNotEquals(appConfig.getOutputForTeamCreation() instanceof ConsoleOutputForTeamCreation,false);
    }

    @Test
    public void getLeagueConcreteTest(){
        AppConfig appConfig = AppConfig.getInstance();
        assertTrue(appConfig.getLeagueConcrete() instanceof LeagueConcrete);
        assertNotEquals(appConfig.getLeagueConcrete(), null);
    }
}
