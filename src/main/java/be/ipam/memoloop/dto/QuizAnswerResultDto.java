package be.ipam.memoloop.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * Response after answering a quiz question — tells the client whether the
 * answer was correct, what the correct one was, and the updated quiz state.
 */
@Value
public class QuizAnswerResultDto implements Serializable {
    Long questionId;
    boolean correct;
    Long correctCardId;
    int score;
    int totalQuestions;
    boolean completed;
}
