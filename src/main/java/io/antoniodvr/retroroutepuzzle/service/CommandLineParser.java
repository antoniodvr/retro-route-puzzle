package io.antoniodvr.retroroutepuzzle.service;

import io.antoniodvr.retroroutepuzzle.App;
import lombok.Getter;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
public class CommandLineParser {

    private static final Option MAP_OPTION = Option.builder("m").longOpt("map")
            .argName("")
            .desc("JSON file containing rooms map")
            .hasArg()
            .type(File.class)
            .required()
            .build();

    private static final Option ROOM_OPTION = Option.builder("r").longOpt("room")
            .argName("")
            .desc("ID of the room to start collecting from")
            .hasArg()
            .type(Number.class)
            .required()
            .build();

    private static final Option ITEMS_OPTION = Option.builder("i").longOpt("items")
            .argName("")
            .desc("List of items to collect in the map")
            .numberOfArgs(Option.UNLIMITED_VALUES)
            .required()
            .build();

    private final File mapFile;
    private final Long roomId;
    private final String[] items;

    private CommandLineParser(Options options, String[] args) throws ParseException {
        Stream.of(MAP_OPTION, ROOM_OPTION, ITEMS_OPTION).forEach(options::addOption);
        CommandLine cli = new DefaultParser().parse(options, args);
        mapFile = (File) cli.getParsedOptionValue(MAP_OPTION.getOpt());
        roomId = ((Number) cli.getParsedOptionValue(ROOM_OPTION.getOpt())).longValue();
        items = cli.getOptionValues(ITEMS_OPTION.getOpt());
    }
    
    public static Optional<CommandLineParser> parse(String[] args) {
        final Options options = new Options();
        CommandLineParser commandLineParser = null;
        try {
            commandLineParser = new CommandLineParser(options, args);
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(App.class.getName(), options, true);
        }
        return Optional.ofNullable(commandLineParser);
    }

}
