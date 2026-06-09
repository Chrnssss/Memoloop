package be.ipam.memoloop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "studied_card")
public class StudiedCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne
    @JoinColumn(name = "learning_deck_id")
    private LearningDeck learningDeck;

    private LocalDateTime lastStudiedAt;

    private int views;

    private int knowledgeLevel;
}
