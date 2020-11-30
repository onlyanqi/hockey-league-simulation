package config;

import presentation.ConsoleOutputForTeamCreation;
import presentation.IConsoleOutputForTeamCreation;
import presentation.IUserInputForTeamCreation;
import presentation.UseInputForTeamCreation;
import simulation.model.IModelFactory;
import simulation.model.ModelFactory;
import persistance.serializers.LeagueDataSerializerDeSerializer;

public class AppConfig {

    private static AppConfig appConfig = null;

    private IUserInputForTeamCreation inputForTeamCreation;
    private IConsoleOutputForTeamCreation outputForTeamCreation;
    private LeagueDataSerializerDeSerializer dataSerializer;
    private IModelFactory modelFactory;

    private AppConfig() {
        inputForTeamCreation = new UseInputForTeamCreation();
        outputForTeamCreation = new ConsoleOutputForTeamCreation();
        modelFactory = ModelFactory.getInstance();
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

    public LeagueDataSerializerDeSerializer getDataSerializerDeSerializer() {
        return dataSerializer;
    }

    public IUserInputForTeamCreation getInputForTeamCreation() {
        return inputForTeamCreation;
    }

    public IConsoleOutputForTeamCreation getOutputForTeamCreation() {
        return outputForTeamCreation;
    }

    public IModelFactory getLeagueConcrete() {
        return modelFactory;
    }

}
