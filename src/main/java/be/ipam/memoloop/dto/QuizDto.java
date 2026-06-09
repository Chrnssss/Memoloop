package be.ipam.memoloop.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link be.ipam.memoloop.model.Quiz}.
 */
@Value
public class QuizDto implements Serializable {
    Long id;
    DeckDto deck;
    int score;
    int totalQuestions;
    boolean completed;
    LocalDateTime createdAt;
    List<QuizQuestionDto> questions;
}
