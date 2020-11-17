package db.dao;

public class CallDBConcrete implements ICallDBFactory{

    @Override
    public ICallDB newCallDB(String procedureName) throws Exception {
        return new CallDB(procedureName);
    }

}
