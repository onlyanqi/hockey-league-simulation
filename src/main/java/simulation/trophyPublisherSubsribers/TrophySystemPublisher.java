package simulation.trophyPublisherSubsribers;


import java.util.HashMap;
//https://refactoring.guru/design-patterns/observer

public class TrophySystemPublisher {
    HashMap<String,ITrophyEventListeners> listeners = new HashMap<String, ITrophyEventListeners>();
    private static String[] eventTypes={"coachStatAbilityUpdate","goalScoreUpdate","penaltyCountUpdate","savesUpdate"};

    private static TrophySystemPublisher instance;

    public static TrophySystemPublisher getInstance() {
        if (instance == null) {
            instance = new TrophySystemPublisher();
        }
        return instance;
    }

    public HashMap<String, ITrophyEventListeners> getListeners() {
        return listeners;
    }

    public void subscribe(String eventType, ITrophyEventListeners listener){

        if(eventType.equals(eventTypes[0]) || eventType.equals(eventTypes[1]) || eventType.equals(eventTypes[2]) || eventType.equals(eventTypes[3])){
            listeners.put(eventType, listener);
        }
    }

    public void unsubscribe(String eventType, ITrophyEventListeners listener){
        if(eventType.equals(eventTypes[0]) || eventType.equals(eventTypes[1]) || eventType.equals(eventTypes[2]) || eventType.equals(eventTypes[3])){
            listeners.remove(eventType,listener);
        }
    }

    public void notify(String eventType, Object object, Integer count){
        for (String currentEventType:listeners.keySet()) {
            if(currentEventType.equals(eventType)){
                ITrophyEventListeners listener = listeners.get(currentEventType);
                listener.update(object,count);
            }
        }
    }
}
