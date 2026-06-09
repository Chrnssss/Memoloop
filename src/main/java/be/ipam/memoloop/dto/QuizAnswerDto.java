package be.ipam.memoloop.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * Body for submitting an answer to a quiz question.
 */
@Value
public class QuizAnswerDto implements Serializable {
    Long questionId;
    Long selectedCardId;
}
