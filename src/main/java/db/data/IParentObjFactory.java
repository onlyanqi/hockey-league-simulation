package db.data;

import simulation.model.ParentObj;

public interface IParentObjFactory {

    void loadParentObj(int id, ParentObj parentObj) throws Exception;
}
