package be.ipam.memoloop.service;

import be.ipam.memoloop.dto.DeckDetailedDto;
import be.ipam.memoloop.dto.CreateDeckDto;
import be.ipam.memoloop.dto.DeckDto;
import be.ipam.memoloop.mapper.DeckDetailedMapper;
import be.ipam.memoloop.mapper.DeckMapper;
import be.ipam.memoloop.model.Deck;
import be.ipam.memoloop.repository.DeckRepository;
import be.ipam.memoloop.repository.LearningDeckRepository;
import be.ipam.memoloop.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {
    private final DeckRepository deckRepository;
    private final DeckMapper deckMapper;
    private final DeckDetailedMapper deckDetailedMapper;
    private final QuizRepository quizRepository;
    private final LearningDeckRepository learningDeckRepository;

    public DeckService(DeckRepository deckRepository,
                       DeckMapper deckMapper,
                       DeckDetailedMapper deckDetailedMapper,
                       QuizRepository quizRepository,
                       LearningDeckRepository learningDeckRepository) {
        this.deckRepository = deckRepository;
        this.deckMapper = deckMapper;
        this.deckDetailedMapper = deckDetailedMapper;
        this.quizRepository = quizRepository;
        this.learningDeckRepository = learningDeckRepository;
    }

    //Read all decks
    public List<DeckDto> getAllDecks() {
        List<Deck> decks = deckRepository.findAll();
        return decks.stream().map(deckMapper::toDto).toList();
    }

    //Read a page of decks
    public List<DeckDto> getDecks(int page, int size) {
        List<Deck> decks = deckRepository.findAll(PageRequest.of(page, size)).getContent();
        return decks.stream().map(deckMapper::toDto).toList();
    }

    //Read a deck by id
    public Deck getDeckById(Long id) {
        return deckRepository.findById(id).orElseThrow(() -> new RuntimeException("Deck not found"));
    }

    //Read detailed deck
    public DeckDetailedDto getDeckDetailedById(Long id) {
        Deck deck = deckRepository.findById(id).orElseThrow(() -> new RuntimeException("Deck not found"));
        return deckDetailedMapper.toDto(deck);
    }

    //add deck
    public DeckDto addDeck(CreateDeckDto createDeckDto) {
        Deck deck = deckMapper.toEntity(createDeckDto);
        Deck savedDeck = deckRepository.save(deck);
        return deckMapper.toDto(savedDeck);
    }

    //delete deck and everything that depends on it
    @Transactional
    public void deleteDeck(Long id) {
        // Quizzes and learning sessions reference the deck but have no back-ref
        // for JPA cascade, so clean them up first. Cards are removed via the
        // cascade on Deck.cards.
        quizRepository.deleteAll(quizRepository.findByDeckId(id));
        learningDeckRepository.deleteAll(learningDeckRepository.findByLearnedDeckId(id));
        deckRepository.deleteById(id);
    }
}
