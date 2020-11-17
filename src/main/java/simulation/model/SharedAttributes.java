package simulation.model;

import db.data.ISharedAttributesFactory;
import org.apache.commons.lang3.StringUtils;


public class SharedAttributes {

    private int id;
    private String name;

    public SharedAttributes() {
    }

    public SharedAttributes(int id) {
        setId(id);
    }

    public SharedAttributes(int id, ISharedAttributesFactory parentObjFactory) throws Exception {
        setId(id);
        parentObjFactory.loadParentObj(id, this);
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

        if (name == null || name.isEmpty()) {
            isValid = false;
        } else {
            isValid = true;
        }

        return isValid;
    }

}
