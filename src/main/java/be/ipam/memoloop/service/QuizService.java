package be.ipam.memoloop.service;

import be.ipam.memoloop.dto.*;
import be.ipam.memoloop.model.Card;
import be.ipam.memoloop.model.Deck;
import be.ipam.memoloop.model.Quiz;
import be.ipam.memoloop.model.QuizQuestion;
import be.ipam.memoloop.repository.DeckRepository;
import be.ipam.memoloop.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuizService {
    private static final int DEFAULT_QUESTION_COUNT = 10;
    private static final int MAX_CHOICES = 4;
    private static final int MIN_DECK_SIZE = 2;

    private final QuizRepository quizRepository;
    private final DeckRepository deckRepository;

    public QuizService(QuizRepository quizRepository, DeckRepository deckRepository) {
        this.quizRepository = quizRepository;
        this.deckRepository = deckRepository;
    }

    @Transactional
    public QuizDto createQuiz(Long deckId, Integer requestedQuestions) {
        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deck not found"));

        List<Card> deckCards = new ArrayList<>(deck.getCards());
        if (deckCards.size() < MIN_DECK_SIZE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Deck needs at least " + MIN_DECK_SIZE + " cards for a quiz");
        }

        int desired = requestedQuestions != null && requestedQuestions > 0
                ? requestedQuestions
                : DEFAULT_QUESTION_COUNT;
        int questionCount = Math.min(desired, deckCards.size());
        int choiceCount = Math.min(MAX_CHOICES, deckCards.size());

        Quiz quiz = new Quiz();
        quiz.setDeck(deck);
        quiz.setTotalQuestions(questionCount);
        quiz.setScore(0);
        quiz.setCompleted(false);
        quiz.setCreatedAt(LocalDateTime.now());

        List<Card> shuffled = new ArrayList<>(deckCards);
        Collections.shuffle(shuffled);
        List<Card> questionCards = new ArrayList<>(shuffled.subList(0, questionCount));

        for (int i = 0; i < questionCards.size(); i++) {
            Card correct = questionCards.get(i);
            List<Card> distractorPool = new ArrayList<>(deckCards);
            distractorPool.remove(correct);
            Collections.shuffle(distractorPool);
            int distractorsNeeded = Math.min(choiceCount - 1, distractorPool.size());
            List<Card> choices = new ArrayList<>(distractorPool.subList(0, distractorsNeeded));
            choices.add(correct);
            Collections.shuffle(choices);

            QuizQuestion q = new QuizQuestion();
            q.setQuiz(quiz);
            q.setCorrectCard(correct);
            q.setChoiceCards(choices);
            q.setOrderIndex(i);
            quiz.getQuestions().add(q);
        }

        Quiz saved = quizRepository.save(quiz);
        return toDto(saved);
    }

    public QuizDto getQuiz(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));
        return toDto(quiz);
    }

    @Transactional
    public QuizAnswerResultDto answer(Long quizId, QuizAnswerDto req) {
        if (req == null || req.getQuestionId() == null || req.getSelectedCardId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing questionId or selectedCardId");
        }
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));
        if (quiz.isCompleted()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Quiz already completed");
        }
        QuizQuestion question = quiz.getQuestions().stream()
                .filter(q -> q.getId().equals(req.getQuestionId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));
        if (question.getCorrect() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Question already answered");
        }
        boolean isCorrect = question.getCorrectCard().getId().equals(req.getSelectedCardId());
        question.setSelectedCardId(req.getSelectedCardId());
        question.setCorrect(isCorrect);
        if (isCorrect) {
            quiz.setScore(quiz.getScore() + 1);
        }
        boolean allAnswered = quiz.getQuestions().stream().allMatch(q -> q.getCorrect() != null);
        if (allAnswered) {
            quiz.setCompleted(true);
        }
        quizRepository.save(quiz);
        return new QuizAnswerResultDto(
                question.getId(),
                isCorrect,
                question.getCorrectCard().getId(),
                quiz.getScore(),
                quiz.getTotalQuestions(),
                quiz.isCompleted()
        );
    }

    public void deleteQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }
        quizRepository.deleteById(id);
    }

    private QuizDto toDto(Quiz quiz) {
        DeckDto deckDto = new DeckDto(quiz.getDeck().getId(), quiz.getDeck().getName());
        List<QuizQuestionDto> questionDtos = quiz.getQuestions().stream()
                .map(this::toQuestionDto)
                .toList();
        return new QuizDto(
                quiz.getId(),
                deckDto,
                quiz.getScore(),
                quiz.getTotalQuestions(),
                quiz.isCompleted(),
                quiz.getCreatedAt(),
                questionDtos
        );
    }

    private QuizQuestionDto toQuestionDto(QuizQuestion q) {
        List<QuizChoiceDto> choices = q.getChoiceCards().stream()
                .map(c -> new QuizChoiceDto(c.getId(), c.getTranslation()))
                .toList();
        boolean answered = q.getCorrect() != null;
        return new QuizQuestionDto(
                q.getId(),
                q.getOrderIndex(),
                q.getCorrectCard().getWord(),
                choices,
                answered,
                q.getSelectedCardId(),
                q.getCorrect(),
                answered ? q.getCorrectCard().getId() : null
        );
    }
}
