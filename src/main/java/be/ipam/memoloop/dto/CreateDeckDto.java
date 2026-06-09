package be.ipam.memoloop.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for creating a new Deck (without id, which is auto-generated)
 */
@Value
public class CreateDeckDto implements Serializable {
    String name;
}

