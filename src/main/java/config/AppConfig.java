package config;

import presentation.ConsoleOutputForTeamCreation;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import presentation.UseInputForTeamCreation;
import simulation.factory.LeagueConcrete;
import simulation.serializers.LeagueDataSerializerDeSerializer;

public class AppConfig {

    private static AppConfig appConfig = null;

    private IUserInputForTeamCreation inputForTeamCreation;
    private IConsoleOutputForTeamCreation outputForTeamCreation;
    private LeagueDataSerializerDeSerializer dataSerializer;
    private LeagueConcrete leagueConcrete;

    private AppConfig() {
        inputForTeamCreation = new UseInputForTeamCreation();
        outputForTeamCreation = new ConsoleOutputForTeamCreation();
        leagueConcrete = new LeagueConcrete();
        dataSerializer = new LeagueDataSerializerDeSerializer();
    }

    public static AppConfig getInstance() {
        if (null == AppConfig.getUniqueInstance()) {
            appConfig = new AppConfig();
        }
        return appConfig;
    }

    private static Object getUniqueInstance() {
        return appConfig;
    }

    public IUserInputForTeamCreation getInputForTeamCreation() {
        return inputForTeamCreation;
    }

    public IConsoleOutputForTeamCreation getOutputForTeamCreation() {
        return outputForTeamCreation;
    }

    public LeagueConcrete getLeagueConcrete() {
        return leagueConcrete;
    }

}
