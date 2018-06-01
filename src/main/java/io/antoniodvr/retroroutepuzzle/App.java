package io.antoniodvr.retroroutepuzzle;

import io.antoniodvr.retroroutepuzzle.exception.RoomNotFoundException;
import io.antoniodvr.retroroutepuzzle.model.Item;
import io.antoniodvr.retroroutepuzzle.model.Maze;
import io.antoniodvr.retroroutepuzzle.model.Route;
import io.antoniodvr.retroroutepuzzle.service.CommandLineParser;
import io.antoniodvr.retroroutepuzzle.service.MazeService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class App {

    public static void main(String[] args) throws IOException {
        try {
            Optional<CommandLineParser> commandLineParser = CommandLineParser.parse(args);
            if (!commandLineParser.isPresent()) {
                System.exit(1);
            }

            Maze maze = MazeService.load(commandLineParser.get().getMapFile());

            Set<Item> itemsToCollect = Arrays.asList(commandLineParser.get().getItems())
                    .stream()
                    .map(Item::new)
                    .collect(toSet());

            Route route = new MazeService().solve(maze, commandLineParser.get().getRoomId(), itemsToCollect);
            route.printMoves(System.out, itemsToCollect.isEmpty());
        } catch (RoomNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
