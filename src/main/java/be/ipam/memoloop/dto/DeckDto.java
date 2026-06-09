package be.ipam.memoloop.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link be.ipam.memoloop.model.Deck}
 */
@Value
public class DeckDto implements Serializable {
    Long id;
    String name;
}