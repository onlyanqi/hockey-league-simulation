package simulation.model;

import db.data.IParentObjFactory;

import java.util.Date;

public class ParentObj {

    String name;
    private int id;
    private Date startDate;
    private Date endDate;

    public ParentObj() {
    }

    public ParentObj(int id) {
        this.id = id;
    }

    public ParentObj(int id, IParentObjFactory parentObjFactory) throws Exception {
        this.id = id;
        parentObjFactory.loadParentObj(id, this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isNull(String input) {
        boolean isNull = false;
        if (input == null) {
            isNull = true;
        }
        return isNull;
    }

    public boolean validEndDate() {
        boolean isValid = false;

        if (getEndDate().compareTo(getStartDate()) >= 0) {
            isValid = true;
        }

        return isValid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNotNull(String input) {
        boolean isNotNull = false;
        if (input != null) {
            isNotNull = true;
        }
        return isNotNull;
    }

    public boolean isNotEmpty(String input) {
        boolean isNotEmpty = false;
        if (input != null && input.trim() != "") {
            isNotEmpty = true;
        }
        return isNotEmpty;
    }

    public boolean validName() {
        boolean isValid = false;

        if (isNotNull(getName()) && isNotEmpty(getName())) {
            isValid = true;
        }

        return isValid;
    }
}
