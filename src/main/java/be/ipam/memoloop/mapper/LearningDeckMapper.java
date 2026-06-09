package be.ipam.memoloop.mapper;

import be.ipam.memoloop.dto.LearningDeckDto;
import be.ipam.memoloop.model.LearningDeck;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LearningDeckMapper {
    LearningDeck toEntity(LearningDeckDto learningDeckDto);

    LearningDeckDto toDto(LearningDeck learningDeck);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    LearningDeck partialUpdate(LearningDeckDto learningDeckDto, @MappingTarget LearningDeck learningDeck);
}