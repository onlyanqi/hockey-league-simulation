package data;

import model.ParentObj;

public interface IParentObjFactory {

    int addParentObj(ParentObj parentObj) throws Exception;
    void loadParentObj(int id, ParentObj parentObj) throws Exception;;

}
