package simulation.GameSubjectObservers;

public class ShotSubject extends GameSubject {
    private static ShotSubject instance;

    public static ShotSubject getInstance() {
        if (instance == null) {
            instance = new ShotSubject();
        }
        return instance;
    }


}
