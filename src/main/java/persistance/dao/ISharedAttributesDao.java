package persistance.dao;

import simulation.model.SharedAttributes;

public interface ISharedAttributesDao {

    void loadParentObj(int id, SharedAttributes sharedAttributes) throws Exception;
}
