package io.antoniodvr.retroroutepuzzle.exception;

public class RoomNotFoundException extends RuntimeException {

    private Long roomId;

    public RoomNotFoundException(Long roomId) {
        super(String.format("Room %d does not exist in the map.", roomId));
        this.roomId = roomId;
    }

}
