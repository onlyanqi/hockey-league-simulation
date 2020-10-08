package state;

public interface IHockeyState {
    public abstract void entry();
    public abstract void process() throws Exception;
    public abstract IHockeyState exit();
}
