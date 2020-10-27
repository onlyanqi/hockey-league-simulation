package config;

import simulation.factory.LeagueConcrete;
import simulation.serializers.LeagueDataSerializer;
import userIO.ConsoleOutputForTeamCreation;
import userIO.IConsoleOutputForTeamCreation;
import userIO.IUserInputForTeamCreation;
import userIO.UseInputForTeamCreation;

public class AppConfig {

    private static AppConfig appConfig = null;

    private IUserInputForTeamCreation inputForTeamCreation;
    private IConsoleOutputForTeamCreation outputForTeamCreation;
    private LeagueDataSerializer dataSerializer;
    private LeagueConcrete leagueConcrete;

    public AppConfig() {
        inputForTeamCreation = new UseInputForTeamCreation();
        outputForTeamCreation = new ConsoleOutputForTeamCreation();
        leagueConcrete = new LeagueConcrete();
        dataSerializer= new LeagueDataSerializer();
    }

    public IUserInputForTeamCreation getInputForTeamCreation() {
        return inputForTeamCreation;
    }

    public IConsoleOutputForTeamCreation getOutputForTeamCreation() {
        return outputForTeamCreation;
    }

    public LeagueDataSerializer getDataSerializer() {
        return dataSerializer;
    }

    public LeagueConcrete getLeagueConcrete() {
        return leagueConcrete;
    }

    public static AppConfig getInstance(){
        if (null == AppConfig.getUniqueInstance()){
            appConfig = new AppConfig();
        }
        return appConfig;
    }

    private static Object getUniqueInstance() {
        return appConfig;
    }

}
