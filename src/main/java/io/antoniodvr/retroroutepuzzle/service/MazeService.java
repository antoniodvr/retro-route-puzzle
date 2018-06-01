package io.antoniodvr.retroroutepuzzle.service;

import com.google.gson.Gson;
import io.antoniodvr.retroroutepuzzle.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

public class MazeService {

    public static Maze load(String filePath) throws IOException {
        BufferedReader mazeReader = Files.newBufferedReader(Paths.get(filePath));
        final Maze maze = new Gson().fromJson(mazeReader, Maze.class);
        return maze.build();
    }

    public static Maze load(File file) throws IOException {
        final Maze maze = new Gson().fromJson(new FileReader(file), Maze.class);
        return maze.build();
    }

    public Route solve(Maze maze, Long roomId, Set<Item> itemsToCollect) {
        Room startRoom = Maze.getRoom(maze.getMap(), roomId);

        Route route = new Route();
        Set<Room> visitedRooms = new HashSet<>();

        // Create a stack and push the first node to visit
        Stack<Room> stack = new Stack<>();
        stack.push(startRoom);
        // Visit nodes with DSF Backtracking
        visitNextRoom(route, stack, visitedRooms, itemsToCollect);

        return route;
    }

    private static void visitNextRoom(Route route, Stack<Room> stack, Set<Room> visitedRooms, Set<Item> itemsToCollect) {
        if(stack.isEmpty() || itemsToCollect.isEmpty()) {
            return;
        }

        // Read the room to visit
        Room room = stack.peek();

        // Look and retain for items in the room
        Set<Item> collectedItems = new HashSet<>(room.getItems());
        collectedItems.retainAll(itemsToCollect);

        // Add current move to the route
        route.getMoves().add(new Move(room, collectedItems));

        // Update items to collect in the next rooms
        itemsToCollect.removeAll(collectedItems);

        // Mark the current room as visited
        visitedRooms.add(room);

        // Get room's neighbors and exclude the ones already visited
        Set<Room> neighbors = new HashSet<>(room.getNeighbors());
        neighbors.removeAll(visitedRooms);

        // Find one of them if exist
        Optional<Room> neighborRoom = neighbors.stream().findFirst();
        if(neighborRoom.isPresent()) {
            // Neighbor exist, so add as next room to visit
            stack.push(neighborRoom.get());
        } else {
            // No more neighbor, so backtracking to the parent node
            stack.pop();
        }

        // Visit the next room
        visitNextRoom(route, stack, visitedRooms, itemsToCollect);
    }

}