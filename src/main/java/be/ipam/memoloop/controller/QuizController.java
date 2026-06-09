package be.ipam.memoloop.controller;

import be.ipam.memoloop.dto.QuizAnswerDto;
import be.ipam.memoloop.dto.QuizAnswerResultDto;
import be.ipam.memoloop.dto.QuizDto;
import be.ipam.memoloop.service.QuizService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quizzes")
class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // Create a quiz from a deck
    @PostMapping("/{deckId}")
    public QuizDto createQuiz(@PathVariable Long deckId,
                              @RequestParam(required = false) Integer questions) {
        return quizService.createQuiz(deckId, questions);
    }

    // Get quiz state
    @GetMapping("/{id}")
    public QuizDto getQuiz(@PathVariable Long id) {
        return quizService.getQuiz(id);
    }

    // Submit answer
    @PostMapping("/{id}/answer")
    public QuizAnswerResultDto answer(@PathVariable Long id, @RequestBody QuizAnswerDto body) {
        return quizService.answer(id, body);
    }

    // Delete quiz
    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
    }
}
