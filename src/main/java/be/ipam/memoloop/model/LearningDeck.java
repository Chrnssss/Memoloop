package be.ipam.memoloop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "learning_deck")
public class LearningDeck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "learned_deck_id", nullable = false)
    private Deck learnedDeck;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "learning_deck_id")
    private List<StudiedCard> myStudiedCards;

    @Transient
    private List<Card> notLearnedCards;

    public List<Card> getNotLearnedCards() {
        List<Card> allCards = new java.util.ArrayList<>(learnedDeck.getCards().stream().toList());
        List<Card> learnedCards = myStudiedCards.stream().map(StudiedCard::getCard).toList();
        allCards.removeAll(learnedCards);
        notLearnedCards = allCards;
        return notLearnedCards;
    }
}
