package be.ipam.memoloop.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * One selectable answer in a quiz question.
 * Only the card id and translation are exposed — the word would give away the answer.
 */
@Value
public class QuizChoiceDto implements Serializable {
    Long cardId;
    String translation;
}
