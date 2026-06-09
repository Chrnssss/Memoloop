package be.ipam.memoloop.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link be.ipam.memoloop.model.LearningDeck}
 */
@Value
public class LearningDeckDto implements Serializable {
    Long id;
    DeckDto learnedDeck;
    List<StudiedCardDto> myStudiedCards;
}