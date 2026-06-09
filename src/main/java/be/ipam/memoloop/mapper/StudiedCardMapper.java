package be.ipam.memoloop.mapper;

import be.ipam.memoloop.dto.StudiedCardDto;
import be.ipam.memoloop.model.StudiedCard;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudiedCardMapper {
    StudiedCard toEntity(StudiedCardDto studiedCardDto);

    StudiedCardDto toDto(StudiedCard studiedCard);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StudiedCard partialUpdate(StudiedCardDto studiedCardDto, @MappingTarget StudiedCard studiedCard);
}