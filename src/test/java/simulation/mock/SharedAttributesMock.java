package simulation.mock;


import simulation.dao.ISharedAttributesDao;
import simulation.model.SharedAttributes;

public class SharedAttributesMock implements ISharedAttributesDao {

    @Override
    public void loadParentObj(int id, SharedAttributes sharedAttributes) {

        switch (new Long(id).intValue()) {
            case 1:
                sharedAttributes.setName("Parent");
                break;

            case 2:
                sharedAttributes.setName(null);
                break;

            case 3:
                sharedAttributes.setName("Invalid Date");
                break;
        }
    }
}
