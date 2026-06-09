package be.ipam.memoloop.controller;

import be.ipam.memoloop.dto.CreateCardDto;
import be.ipam.memoloop.service.CardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // get card by id
    @GetMapping("/{id}")
    public Object getCardById(@PathVariable Long id) {
        return cardService.getCardById(id);
    }

    // add card
    @PostMapping
    public Object addCard(@RequestBody CreateCardDto createCardDto) {
        return cardService.addCard(createCardDto);
    }
}
