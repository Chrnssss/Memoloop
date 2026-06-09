package be.ipam.memoloop.service;

import be.ipam.memoloop.dto.LearningDeckDto;
import be.ipam.memoloop.dto.StudiedCardDto;
import be.ipam.memoloop.mapper.LearningDeckMapper;
import be.ipam.memoloop.mapper.StudiedCardMapper;
import be.ipam.memoloop.model.Deck;
import be.ipam.memoloop.model.LearningDeck;
import be.ipam.memoloop.model.StudiedCard;
import be.ipam.memoloop.repository.LearningDeckRepository;
import be.ipam.memoloop.repository.StudiedCardRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class LearningDeckService {
    private final LearningDeckRepository learningDeckRepository;
    private final LearningDeckMapper learningDeckMapper;
    private final DeckService deckService;
    private final StudiedCardRepository studiedCardRepository;
    private final StudiedCardMapper studiedCardMapper;

    public LearningDeckService(LearningDeckRepository learningDeckRepository,
                               LearningDeckMapper learningDeckMapper,
                               DeckService deckService,
                               StudiedCardRepository studiedCardRepository,
                               StudiedCardMapper studiedCardMapper) {
        this.learningDeckRepository = learningDeckRepository;
        this.learningDeckMapper = learningDeckMapper;
        this.deckService = deckService;
        this.studiedCardRepository = studiedCardRepository;
        this.studiedCardMapper = studiedCardMapper;
    }

    //Create learning deck
    public LearningDeckDto createLearningDeck(long deckId) {
        Deck deck = deckService.getDeckById(deckId);
        if (deck == null) {
            throw new RuntimeException("Deck not found");
        }
        LearningDeck learningDeck = new LearningDeck();
        learningDeck.setLearnedDeck(deck);
        LearningDeck savedLearningDeck = learningDeckRepository.save(learningDeck);
        return learningDeckMapper.toDto(savedLearningDeck);
    }

    //Get learning deck by id
    public LearningDeckDto getLearningDeckById(long id) {
        LearningDeck learningDeck = learningDeckRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Learning deck not found"));
        return learningDeckMapper.toDto(learningDeck);
    }

    @Transactional
    public StudiedCardDto createNextStudiedCard(long learningDeckId){
        var learningDeck = learningDeckRepository.findById(learningDeckId)
                .orElseThrow(() -> new RuntimeException("Learning deck not found"));

        var studiedCard = new StudiedCard();
        studiedCard.setLearningDeck(learningDeck);

        learningDeck.getNotLearnedCards().stream().findFirst().ifPresent(card -> {
            studiedCard.setCard(card);
            studiedCardRepository.save(studiedCard);
            learningDeck.getMyStudiedCards().add(studiedCard);
            learningDeckRepository.save(learningDeck);
        });

        return studiedCardMapper.toDto(studiedCard);
    }
}
