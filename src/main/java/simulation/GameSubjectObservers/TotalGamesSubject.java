package simulation.GameSubjectObservers;

public class TotalGamesSubject extends GameSubject {
    private static TotalGamesSubject instance;

    public static TotalGamesSubject getInstance() {
        if (instance == null) {
            instance = new TotalGamesSubject();
        }
        return instance;
    }

}
