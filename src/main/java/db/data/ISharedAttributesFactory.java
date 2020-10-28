package db.data;

import simulation.model.SharedAttributes;

public interface ISharedAttributesFactory {

    void loadParentObj(int id, SharedAttributes sharedAttributes) throws Exception;
}
