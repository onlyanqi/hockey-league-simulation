package simulation.model;


public abstract class SharedAttributes {

    private int id;
    private String name;

    public SharedAttributes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean validName() {
        boolean isValid = false;

        isValid = name != null && !name.isEmpty();

        return isValid;
    }

}
