package db.dao;

import presentation.ConsoleOutput;

public class DBExceptionLog {

    ICallDBFactory callDBFactory;

    public DBExceptionLog() {
        callDBFactory = new CallDBConcrete();
    }

    public void printLog(String output) {
        ConsoleOutput consoleOutput = ConsoleOutput.getInstance();
        consoleOutput.printMsgToConsole(output);
    }



}
