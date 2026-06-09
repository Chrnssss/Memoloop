package be.ipam.memoloop.repository;

import be.ipam.memoloop.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}