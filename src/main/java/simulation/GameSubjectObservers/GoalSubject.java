package simulation.GameSubjectObservers;

public class GoalSubject extends GameSubject {
    private static GoalSubject instance;

    public static GoalSubject getInstance() {
        if (instance == null) {
            instance = new GoalSubject();
        }
        return instance;
    }

}
