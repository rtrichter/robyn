package robyn.cliTools;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

/**
 * CLITest
 *
 * @author Ryan Richter
 */
public class CLITest {

    @Test
    public void testPrintNoFormat() {
        ByteArrayOutputStream binOut = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(binOut);
        InputStream input = InputStream.nullInputStream();
        CLI cli = new CLI(input, output);
        String expected = "Hello World";

        cli.print("Hello World");
        String actual = binOut.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testPrintFormat() {
        ByteArrayOutputStream binOut = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(binOut);
        InputStream input = InputStream.nullInputStream();
        CLI cli = new CLI(input, output);
        String expected = "Happy birthday {name}! You are 999 years old!";

        cli.print("Happy birthday {%s}! You are %d years old!", "name", 999);
        String actual = binOut.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testPrintNothing() {
        ByteArrayOutputStream binOut = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(binOut);
        InputStream input = InputStream.nullInputStream();
        CLI cli = new CLI(input, output);
        String expected = "";

        cli.print("");
        String actual = binOut.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testReadLineHello() {
        String inputString = "Hello";
        InputStream input = new ByteArrayInputStream(
                inputString.getBytes(Charset.forName("UTF-8")));
        PrintStream output = null;
        CLI cli = new CLI(input, output);
        String expected = "Hello";

        String actual = cli.readLine();

        assertEquals(expected, actual);
    }

    @Test
    public void parseCommandStringEmpty() {
        // setup
        String inputString = "";
        String[] expected = new String[0];

        InputStream input = new ByteArrayInputStream(
                inputString.getBytes(Charset.forName("UTF-8")));
        ByteArrayOutputStream binOut = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(binOut);
        CLI cli = new CLI(input, output);

        // invoke
        String[] actual = cli.parseCommandString(inputString);

        // assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void parseCommandStringLeadingWhitespace() {
        // setup
        String inputString = "  add 2";
        String[] expected = { "add", "2" };

        InputStream input = new ByteArrayInputStream(
                inputString.getBytes(Charset.forName("UTF-8")));
        ByteArrayOutputStream binOut = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(binOut);
        CLI cli = new CLI(input, output);

        // invoke
        String[] actual = cli.parseCommandString(inputString);

        // assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void parseCommandStringExtraWhitespaceBetweenArgs() {
        // setup
        String inputString = "add  2";
        String[] expected = { "add", "2" };

        InputStream input = new ByteArrayInputStream(
                inputString.getBytes(Charset.forName("UTF-8")));
        ByteArrayOutputStream binOut = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(binOut);
        CLI cli = new CLI(input, output);

        // invoke
        String[] actual = cli.parseCommandString(inputString);

        // assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void parseCommandStringTrailingWhitespace() {
        // setup
        String inputString = "add 2  ";
        String[] expected = { "add", "2" };

        InputStream input = new ByteArrayInputStream(
                inputString.getBytes(Charset.forName("UTF-8")));
        ByteArrayOutputStream binOut = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(binOut);
        CLI cli = new CLI(input, output);

        // invoke
        String[] actual = cli.parseCommandString(inputString);

        // assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void parseCommandStringWithString() {
        // setup
        String inputString = "echo \"Hello World\"";
        String[] expected = { "echo", "Hello World" };

        InputStream input = new ByteArrayInputStream(
                inputString.getBytes(Charset.forName("UTF-8")));
        ByteArrayOutputStream binOut = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(binOut);
        CLI cli = new CLI(input, output);

        // invoke
        String[] actual = cli.parseCommandString(inputString);

        // assert
        assertArrayEquals(expected, actual);
    }

    @Test
    public void parseCommandString3Args() {
        // setup
        String inputString = "add 2 8";
        String[] expected = { "add", "2", "8" };

        InputStream input = new ByteArrayInputStream(
                inputString.getBytes(Charset.forName("UTF-8")));
        ByteArrayOutputStream binOut = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(binOut);
        CLI cli = new CLI(input, output);

        // invoke
        String[] actual = cli.parseCommandString(inputString);

        // assert
        assertArrayEquals(expected, actual);
    }
}
