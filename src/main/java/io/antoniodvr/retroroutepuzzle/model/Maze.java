package io.antoniodvr.retroroutepuzzle.model;

import io.antoniodvr.retroroutepuzzle.exception.RoomNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Getter
@Setter
public class Maze {

    private List<Room> rooms;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<Long, Room> mazeMap;

    public Map<Long, Room> getMap() {
        if(mazeMap == null) {
            mazeMap = rooms.stream().collect(Collectors.toMap(Room::getId, Function.identity()));
        }
        return mazeMap;
    }

    public Maze build() {
        getRooms().stream().forEach(room -> room.calculateNeighbors(getMap()));
        return this;
    }

    public static Room getRoom(Map<Long, Room> mazeMap, Long roomId) {
        return Optional.ofNullable(mazeMap.get(roomId))
                .orElseThrow(() -> new RoomNotFoundException(roomId));
    }

}
