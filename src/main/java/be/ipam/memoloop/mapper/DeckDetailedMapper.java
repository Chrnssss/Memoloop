package be.ipam.memoloop.mapper;

import be.ipam.memoloop.dto.DeckDetailedDto;
import be.ipam.memoloop.model.Deck;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeckDetailedMapper {
    Deck toEntity(DeckDetailedDto deckDetailedDto);

    @AfterMapping
    default void linkCards(@MappingTarget Deck deck) {
        deck.getCards().forEach(card -> card.setDeck(deck));
    }

    DeckDetailedDto toDto(Deck deck);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Deck partialUpdate(DeckDetailedDto deckDetailedDto, @MappingTarget Deck deck);
}