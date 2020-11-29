package simulation.GameSubjectObservers;

public class SaveSubject extends GameSubject {
    private static SaveSubject instance;

    public static SaveSubject getInstance() {
        if (instance == null) {
            instance = new SaveSubject();
        }
        return instance;
    }
}
