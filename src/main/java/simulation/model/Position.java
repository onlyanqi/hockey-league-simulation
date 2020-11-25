package simulation.model;

import java.util.Random;

public enum Position {
    FORWARD {
        public String toString() {
            return "forward";
        }
    },
    DEFENSE {
        public String toString() {
            return "defense";
        }
    },
    GOALIE {
        public String toString() {
            return "goalie";
        }
    };

    public static Position generateRandomPosition() {
        int wholePortion = 10;
        int forwardPortion = 5;
        int defensePortion = 4;
        int rand = new Random().nextInt(wholePortion);
        if (rand < forwardPortion) {
            return FORWARD;
        } else if (rand < forwardPortion + defensePortion) {
            return DEFENSE;
        } else {
            return GOALIE;
        }
    }
}