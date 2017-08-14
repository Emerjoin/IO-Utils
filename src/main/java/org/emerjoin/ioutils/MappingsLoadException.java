package org.emerjoin.ioutils;

/**
 * @author Mario Junior.
 */
public class MappingsLoadException extends RuntimeException {

    public MappingsLoadException(String message){
        super(message);
    }

    public MappingsLoadException(String message, Throwable cause){
        super(message,cause);
    }

}
