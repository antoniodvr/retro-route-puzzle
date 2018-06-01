package io.antoniodvr.retroroutepuzzle.service;

import io.antoniodvr.retroroutepuzzle.model.Item;
import io.antoniodvr.retroroutepuzzle.model.Maze;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class MazeServiceTest {

    private static Maze maze;

    @BeforeClass
    public static void setup() throws IOException {
        maze = MazeService.load("src/test/resources/map01.json");
    }

    @Test
    public void should_load_maze_from_file() {
        Assert.assertNotNull(maze);
    }

    @Test
    public void should_maze_map_be_designed() {
        Assert.assertFalse(maze.getMap().isEmpty());
    }

    @Test
    public void should_neighbors_be_calculated() {
        maze.getRooms().stream().forEach(room -> Assert.assertNotNull(room.getNeighbors()));
    }

    @Test
    public void should_all_rooms_be_adjacent() {
        maze.getRooms().forEach(room -> {
            Assert.assertFalse(room.getNeighbors().isEmpty());
        });
    }

    @Test
    public void should_all_items_be_collected() {
        Set<Item> itemsToCollect = Arrays.asList("Knife", "Potted Plant")
                .stream()
                .map(Item::new)
                .collect(toSet());

        new MazeService().solve(maze, 2L, itemsToCollect);
        Assert.assertTrue(itemsToCollect.isEmpty());
    }

    @Test
    public void should_all_items_be_collected_in_the_first_room() {
        Set<Item> itemsToCollect = Arrays.asList("Knife")
                .stream()
                .map(Item::new)
                .collect(toSet());

        new MazeService().solve(maze, 3L, itemsToCollect);
        Assert.assertTrue(itemsToCollect.isEmpty());
    }

    @Test
    public void should_none_of_items_be_collected() {
        Set<Item> itemsToCollect = Arrays.asList("Spoon", "Hammer")
                .stream()
                .map(Item::new)
                .collect(toSet());

        new MazeService().solve(maze, 2L, itemsToCollect);
        Assert.assertEquals(2, itemsToCollect.size());
    }

    @Test
    public void should_one_of_items_be_collected() {
        Set<Item> itemsToCollect = Arrays.asList("Knife", "Hammer")
                .stream()
                .map(Item::new)
                .collect(toSet());

        new MazeService().solve(maze, 2L, itemsToCollect);
        Assert.assertEquals(1, itemsToCollect.size());
        Assert.assertEquals("Hammer", itemsToCollect.iterator().next().getName());
    }

}
