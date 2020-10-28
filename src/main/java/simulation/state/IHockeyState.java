package simulation.state;

public interface IHockeyState {
    void entry() throws Exception;

    void process() throws Exception;

    IHockeyState exit();

}
