package br.com.caelum.tubaina;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.junit.Before;
import org.junit.Test;

public class TubainaOptionTest {

    private CommandLineParser commandLineParser;
    private Options options;

    @Before
    public void setUp() {
        commandLineParser = new PosixParser();
        options = new Options();
        for (TubainaOption tubainaOption : TubainaOption.values()) {
            options.addOption(tubainaOption.buildOption());
        }
    }

    @Test
    public void shouldNotExistOptionsWithSameShortName() throws Exception {
        List<TubainaOption> options = Arrays.asList(TubainaOption.values());
        HashSet<Character> shortNames = new HashSet<Character>();
        for (TubainaOption option : options) {
            Character shortName = option.getShortName();
            if (shortNames.contains(shortName)) {
                fail("Two options with the char (" + shortName
                        + ") as short name. One of them should not exist!");
            }
            shortNames.add(shortName);
        }
    }

    @Test
    public void shouldParseIfdefsCorrectly() throws ParseException {
        String[] args = new String[] { "-i", "input-dir", "-o", "output-dir", "-n", "book-title",
                "-c", "100", "-e", "ifdef1", "ifdef2", "-t", "template-dir" };
        CommandLine commandLine = commandLineParser.parse(options, args);

        String[] optionValues = commandLine.getOptionValues('e');
        assertEquals("ifdef1", optionValues[0]);
        assertEquals("ifdef2", optionValues[1]);
    }

    @Test
    public void shouldParseTemplateDirCorrectly() throws ParseException {
        String[] args = new String[] { "-i", "input-dir", "-o", "output-dir", "-n", "book-title",
                "-c", "100", "-e", "ifdef1", "ifdef2", "-t", "template-dir" };
        CommandLine commandLine = commandLineParser.parse(options, args);

        String optionValue = commandLine.getOptionValue('t');
        assertEquals("template-dir", optionValue);
    }

}
