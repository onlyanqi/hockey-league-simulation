package simulation.model;

public class Manager extends ParentObj{
    public Manager() {
    }

    public Manager(int id) {
        setId(id);
    }

//    public Manager(int id, IManagerFactory factory) throws Exception {
//        setId(id);
//        factory.loadManagerByLeagueId(id, this);
//    }

    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
