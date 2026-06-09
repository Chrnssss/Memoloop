package be.ipam.memoloop.repository;

import be.ipam.memoloop.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, Long> {
}