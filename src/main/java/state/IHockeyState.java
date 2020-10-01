package state;

public interface IHockeyState {
    public abstract void entry();
    public abstract void process();
    public abstract void exit();
}
