package be.ipam.memoloop.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link be.ipam.memoloop.model.StudiedCard}
 */
@Value
public class StudiedCardDto implements Serializable {
    Long id;
    CardDto card;
    LocalDateTime lastStudiedAt;
    int views;
    int knowledgeLevel;
}