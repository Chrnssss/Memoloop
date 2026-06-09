package be.ipam.memoloop.controller;

import be.ipam.memoloop.dto.LearningDeckDto;
import be.ipam.memoloop.dto.StudiedCardDto;
import be.ipam.memoloop.service.LearningDeckService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learningdecks")
class LearningDeckController {
    private final LearningDeckService learningDeckService;

    public LearningDeckController(LearningDeckService learningDeckService) {
        this.learningDeckService = learningDeckService;
    }

    //Get LearningDeck by id
    @GetMapping("/{id}")
    public LearningDeckDto getLearningDeckById(@PathVariable long id) {
        return learningDeckService.getLearningDeckById(id);
    }

    //Create LearningDeck
    @PostMapping("/{deckId}")
    public LearningDeckDto createLearningDeck(@PathVariable long deckId) {
        return learningDeckService.createLearningDeck(deckId);
    }

    //Create next studied card
    @PostMapping("/{learningDeckId}/next")
    public StudiedCardDto createNextStudiedCard(@PathVariable long learningDeckId) {
        return learningDeckService.createNextStudiedCard(learningDeckId);
    }

}
