package io.antoniodvr.retroroutepuzzle.model;

import lombok.Getter;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.stream.Collectors;


public class Route {

    @Getter
    private LinkedList<Move> moves;

    public Route() {
        moves = new LinkedList<>();
    }

    public void printMoves(PrintStream printStream, boolean haveAllItemsBeCollected) {
        printStream.printf("\n%-3s %-16s %-64s\n", "ID", "Room", "Collected items");
        getMoves().stream().forEach(move -> {
            String items = String.join(",", move.getCollectedItems().stream().map(Item::getName).collect(Collectors.toList()));
            printStream.printf("%-3d %-16s %-64s\n", move.getRoom().getId(), move.getRoom().getName(), items.isEmpty() ? "None" : items);
        });

        final String message = haveAllItemsBeCollected ? "All items have been collected!" : "Some items have not been collected!";
        printStream.println(String.format("\n%s\n", message));
    }

}
