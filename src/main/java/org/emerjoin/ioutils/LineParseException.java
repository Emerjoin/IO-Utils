package org.emerjoin.ioutils;

/**
 * @author Mario Junior.
 */
public class LineParseException extends MappingsLoadException {

    private int number = 0;

    public LineParseException(int number, String message){
        this(number,message,null);
    }

    private void setNumber(int number){
        if(number<1)
            throw new IllegalArgumentException("Line number must be higher than 0");
        this.number = number;
    }

    public LineParseException(int number, String message, Throwable cause){
        super(String.format("Failed to parse line number [%d] : %s",number,message),cause);
        this.setNumber(number);
    }

    public int getNumber() {
        return number;
    }
}
