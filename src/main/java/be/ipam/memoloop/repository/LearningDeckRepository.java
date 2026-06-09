package be.ipam.memoloop.repository;

import be.ipam.memoloop.model.LearningDeck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningDeckRepository extends JpaRepository<LearningDeck, Long> {
    List<LearningDeck> findByLearnedDeckId(Long deckId);
}