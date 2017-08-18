import org.emerjoin.ioutils.LineParseException;
import org.emerjoin.ioutils.Mappings;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * @author Mario Junior.
 */
public class MappingsTest {

    private static File getTestCase(int number){

        return new File("test-cases"
                +File.separator +"case"+ String
                .format("%d",number)+".map");

    }


    @Test(expected = IllegalArgumentException.class)
    public void load_from_file_must_fail_if_file_does_not_Exist(){

        Mappings.load(new File("life.map"));


    }


    @Test
    public void load_must_fail_if_delimiter_is_first_line_character(){

        try {

            Mappings.load(getTestCase(2));
            fail();

        }catch (LineParseException ex){

            assertEquals(1,ex.getNumber());

        }

    }


    @Test
    public void load_must_fail_if_delimiter_is_last_line_character_without_escaping(){


        try {

            Mappings.load(getTestCase(5));
            fail();

        }catch (LineParseException ex){

            assertEquals(2,ex.getNumber());

        }


    }

    @Test
    public void load_must_fail_if_no_delimiter_is_found_in_a_line(){

        try {

            Mappings.load(getTestCase(7));
            fail();

        }catch (LineParseException ex){

            assertEquals(4,ex.getNumber());

        }

    }


    @Test
    public void escaped_delimiters_be_processed_an_escape_characters_removed(){

        Map<String,String> map = Mappings.load(getTestCase(3));
        System.out.println(map.toString());
        assertEquals(3,map.size());

        String fName = map.get("full=name");
        assertNotNull(fName);
        assertEquals("nome=completo",fName);

        String eAddress = map.get("email address");
        assertNotNull(eAddress);
        assertEquals("endereco de email",eAddress);

        String age = map.get("age");
        assertNotNull(age);
        assertEquals("idade",age);

    }

    @Test
    public void if_a_character_is_escaped_twice_then_one_escape_character_must_be_kept(){

        Map<String,String> map = Mappings.load(getTestCase(8));
        String info = map.get("info");
        assertNotNull(info);
        assertEquals("o sinal \\= serve para demonstrar igualdade",info);


    }


    @Test
    public void load_must_fail_if_no_delimiter_is_found(){
        try {

            Map<String, String> map = Mappings.load(getTestCase(7));
            fail();

        }catch (LineParseException ex){

            assertEquals(4,ex.getNumber());

        }
    }

    @Test
    public void all_values_defined_in_the_mappings_file_must_be_parsed(){

        Map<String, String> map = Mappings.load(getTestCase(1));
        String name = map.get("name");
        assertNotNull(name);
        assertEquals("nome",name);

        String age = map.get("age");
        assertNotNull(age);
        assertEquals("idade",age);

        String height = map.get("height");
        assertNotNull(height);
        assertEquals("altura",height);

    }


    @Test
    public void there_should_be_a_problem_parsing_when_there_empty_lines(){

        Map<String, String> map = Mappings.load(getTestCase(9));
        assertEquals(3,map.size());

        String name = map.get("name");
        assertNotNull(name);
        assertEquals("nome",name);

        String age = map.get("age");
        assertNotNull(age);
        assertEquals("idade",age);

        String height = map.get("height");
        assertNotNull(height);
        assertEquals("altura",height);


    }


    @Test
    public void lines_that_start_or_end_with_spaces_must_be_trimmed(){


        Map<String, String> map = Mappings.load(getTestCase(10));
        assertEquals(2,map.size());

        System.out.println(map);

        String brand = map.get("brand");
        assertNotNull(brand);
        assertEquals("Toy ota",brand);

        String model = map.get("model");
        assertNotNull(model);
        assertEquals("Vitz",model);



    }
}
