package be.ipam.memoloop.mapper;

import be.ipam.memoloop.dto.CreateCardDto;
import be.ipam.memoloop.dto.CardDto;
import be.ipam.memoloop.model.Card;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {
    Card toEntity(CardDto cardDto);

    Card toEntity(CreateCardDto createCardDto);

    CardDto toDto(Card card);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Card partialUpdate(CardDto cardDto, @MappingTarget Card card);
}