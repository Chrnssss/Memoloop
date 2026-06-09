package be.ipam.memoloop.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link be.ipam.memoloop.model.QuizQuestion}.
 * correctCardId is null until the question is answered.
 */
@Value
public class QuizQuestionDto implements Serializable {
    Long id;
    int orderIndex;
    String prompt;
    List<QuizChoiceDto> choices;
    boolean answered;
    Long selectedCardId;
    Boolean correct;
    Long correctCardId;
}
