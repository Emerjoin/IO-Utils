package org.emerjoin.ioutils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Mario Junior.
 */
public class Mappings {

    private static final char DELIMITER = '=';

    public static Map<String,String> load(File file){
        if(file==null)
            throw new IllegalArgumentException("Mappings File must not be null");
        if(!file.exists())
            throw new IllegalArgumentException(String.format("Mappings File does not exist : %s",
                    file.getAbsolutePath()));


        try {

            return load(new FileInputStream(file));

        }catch (IOException ex){

            throw new MappingsLoadException(String.format("Failed to load mappings from File : %s",
                file),ex);

        }

    }

    public static Map<String,String> load(InputStream stream){
        if(stream==null)
            throw new IllegalArgumentException("InputStream instance must not be null");

        Map<String, String> values = new HashMap<String, String>();

        try {

            Scanner scanner = new Scanner(stream, "UTF-8");
            int number = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.trim().isEmpty()){
                    number++;
                    continue;
                }

                parseLine(number, line, values);
                number++;
            }

        }catch (MappingsLoadException ex){
            throw ex;
        }catch (Exception ex){
            throw new MappingsLoadException("Failed to load Mappings",ex);
        }

        return values;
    }


    private static void parseLine(int number, String line, Map<String,String> target){
        line = line.trim();
        char[] chars = line.toCharArray();
        char prev = Character.MIN_VALUE;
        int charIndex = 0;
        for(char ch : chars){
            if(ch== DELIMITER &&prev=='\\') {
                prev = ch;
                charIndex++;
                continue;
            }if(ch== DELIMITER){
                if(charIndex==line.length()-1)
                    throw new LineParseException(number,"Can't place DELIMITER as the last character");
                if(charIndex==0)
                    throw new LineParseException(number,"Can't place DELIMITER as the first character");
                split(charIndex,line,target);
                return;
            }
            prev = ch;
            charIndex++;
        }

        throw new LineParseException(number,"No DELIMITER [=] found.");

    }


    private static void split(int delimiterIndex,String line, Map<String,String> target){
        String value = line.substring(delimiterIndex+1);
        value = value.replaceAll("\\\\=","=");
        String key = line.substring(0,delimiterIndex);
        key = key.replaceAll("\\\\=","=");

        char[] kchars = key.toCharArray();
        if(kchars[key.length()-1]==' ')
            key = key.substring(0,key.length()-1);

        char[] vchars = value.toCharArray();
        if(vchars[0]==' ')
            value = value.substring(1);

        target.put(key,value);
    }

}
