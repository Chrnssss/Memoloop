package be.ipam.memoloop.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link be.ipam.memoloop.model.Deck}
 */
@Value
public class DeckDetailedDto implements Serializable {
    Long id;
    String name;
    Set<CardDto> cards;
}