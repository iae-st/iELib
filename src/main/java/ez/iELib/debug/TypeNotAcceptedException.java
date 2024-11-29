package ez.iELib.debug;

import java.lang.reflect.Type;

public class TypeNotAcceptedException extends Exception {
    public TypeNotAcceptedException(Class expected, Class result) {
        super("Expected type " + expected.getName() + " but recieved " + result.getName());
    }

    public TypeNotAcceptedException(Class expected, Type recieved) {
        super("Expected type " + expected.getName() + " but recieved " + recieved.getTypeName());
    }

}