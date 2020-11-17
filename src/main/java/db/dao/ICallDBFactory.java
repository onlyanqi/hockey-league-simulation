package db.dao;

public interface ICallDBFactory {

    ICallDB newCallDB(String procedureName) throws Exception;

}
