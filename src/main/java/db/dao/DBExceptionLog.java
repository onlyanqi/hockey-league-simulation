package db.dao;

import presentation.ConsoleOutput;

public class DBExceptionLog {

    public DBExceptionLog() {

    }

    public void printLog(String output) {
        ConsoleOutput consoleOutput = ConsoleOutput.getInstance();
        consoleOutput.printMsgToConsole(output);
    }

}
