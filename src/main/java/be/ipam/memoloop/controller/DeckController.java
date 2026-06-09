package be.ipam.memoloop.controller;

import be.ipam.memoloop.dto.CreateCardDto;
import be.ipam.memoloop.dto.CreateDeckDto;
import be.ipam.memoloop.dto.CardDto;
import be.ipam.memoloop.dto.DeckDetailedDto;
import be.ipam.memoloop.dto.DeckDto;
import be.ipam.memoloop.service.CardService;
import be.ipam.memoloop.service.DeckService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/decks")
class DeckController {
    private final DeckService deckService;
    private final CardService cardService;

    public DeckController(DeckService deckService, CardService cardService) {
        this.deckService = deckService;
        this.cardService = cardService;
    }

    // read all deck
    @GetMapping("/all")
    public List<DeckDto> getAllDecks() {
        return deckService.getAllDecks();
    }

    // read paged decks
    @GetMapping("/paged")
    public List<DeckDto> getDecks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return deckService.getDecks(page, size);
    }

    // read deck by id
    @GetMapping("/{id}")
    public DeckDetailedDto getDeckDetailedById(@PathVariable Long id) {
        return deckService.getDeckDetailedById(id);
    }

    // create deck
    @PostMapping
    public DeckDto addDeck(@RequestBody CreateDeckDto createDeckDto) {
        return deckService.addDeck(createDeckDto);
    }

    // create card in deck
    @PostMapping("/{deckId}/cards")
    public CardDto addCardToDeck(@PathVariable Long deckId, @RequestBody CreateCardDto createCardDto) {
        return cardService.addCardToDeck(deckId, createCardDto);
    }

    // delete deck
    @DeleteMapping
    public void deleteDeck(@RequestParam Long id) {
        deckService.deleteDeck(id);
    }
}
