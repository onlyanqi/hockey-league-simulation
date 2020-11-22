package simulation.model;

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
    }
}