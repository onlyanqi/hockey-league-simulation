package db.dao;

import presentation.ConsoleOutput;
import simulation.factory.ValidationConcrete;
import validator.IValidation;

public class DBExceptionLog {

    private IValidation validation;

    public IValidation getValidation() {
        return validation;
    }

    public void setValidation(IValidation validation) {
        this.validation = validation;
    }

    public void printLog(String output) {
        ConsoleOutput consoleOutput = ConsoleOutput.getInstance();
        consoleOutput.printMsgToConsole(output);
    }

    public DBExceptionLog(){
        ValidationConcrete validationConcrete = new ValidationConcrete();
        validation = validationConcrete.newValidation();
    }

}
