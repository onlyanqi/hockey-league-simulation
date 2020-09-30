package model;

import data.IParentObjFactory;

import java.util.Date;

public class ParentObj {

    public ParentObj() { }

    public ParentObj(long id){
        this.id = id;
    }

    public ParentObj(long id, IParentObjFactory parentObjFactory){
        this.id = id;
        parentObjFactory.loadParentObj(id, this);
    }

    private long id;

    private String name;

    private Date startDate;

    private Date endDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isNull(String input){
        boolean isNull = false;
        if(input == null){
            isNull = true;
        }
        return isNull;
    }

    public boolean isNotNull(String input){
        boolean isNotNull = false;
        if(input != null){
            isNotNull = true;
        }
        return isNotNull;
    }

    public boolean isNotEmpty(String input){
        boolean isNotEmpty = false;
        if(input != null && input.trim() != ""){
            isNotEmpty = true;
        }
        return isNotEmpty;
    }

    public boolean validName(){
        boolean isValid = false;

        if(isNotNull(getName()) && isNotEmpty(getName())){
            isValid = true;
        }

        return isValid;
    }

    public boolean validEndDate(){
        boolean isValid = false;

        if(getEndDate().compareTo(getStartDate()) >= 0){
            isValid = true;
        }

        return isValid;
    }

}
