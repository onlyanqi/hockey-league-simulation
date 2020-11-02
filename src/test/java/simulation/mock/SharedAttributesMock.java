package simulation.mock;


import db.data.ISharedAttributesFactory;
import simulation.model.SharedAttributes;

public class SharedAttributesMock implements ISharedAttributesFactory {

    @Override
    public void loadParentObj(int id, SharedAttributes sharedAttributes) {

        switch (new Long(id).intValue()) {
            case 1:
                //all valid data
                sharedAttributes.setName("Parent");
                break;

            case 2:
                //invalid name
                sharedAttributes.setName(null);
                break;

            case 3:
                //invalid date
                sharedAttributes.setName("Invalid Date");
                break;
        }
    }
}
