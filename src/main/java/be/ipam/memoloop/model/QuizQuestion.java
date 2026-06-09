package be.ipam.memoloop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "quiz_question")
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(optional = false)
    @JoinColumn(name = "correct_card_id", nullable = false)
    private Card correctCard;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "quiz_question_choices",
            joinColumns = @JoinColumn(name = "quiz_question_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    @OrderColumn(name = "choice_order")
    private List<Card> choiceCards = new ArrayList<>();

    @Column(name = "order_index", nullable = false)
    private int orderIndex;

    @Column(name = "selected_card_id")
    private Long selectedCardId;

    @Column(name = "correct")
    private Boolean correct;
}
