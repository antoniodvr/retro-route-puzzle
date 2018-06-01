package io.antoniodvr.retroroutepuzzle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class Move {

    private Room room;
    private Set<Item> collectedItems;

}
