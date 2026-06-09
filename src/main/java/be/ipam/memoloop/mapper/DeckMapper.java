package be.ipam.memoloop.mapper;

import be.ipam.memoloop.dto.CreateDeckDto;
import be.ipam.memoloop.dto.DeckDto;
import be.ipam.memoloop.model.Deck;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeckMapper {
    Deck toEntity(DeckDto deckDto);

    Deck toEntity(CreateDeckDto createDeckDto);

    DeckDto toDto(Deck deck);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Deck partialUpdate(DeckDto deckDto, @MappingTarget Deck deck);
}