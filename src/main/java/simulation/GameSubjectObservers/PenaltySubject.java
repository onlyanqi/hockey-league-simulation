package simulation.GameSubjectObservers;

public class PenaltySubject extends GameSubject {
    private static PenaltySubject instance;

    public static PenaltySubject getInstance() {
        if (instance == null) {
            instance = new PenaltySubject();
        }
        return instance;
    }
}
