package simulation.state;

public interface IHockeyState {
    public abstract void entry() throws Exception;
    public abstract void process() throws Exception;
    public abstract IHockeyState exit();
}
