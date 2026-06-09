package be.ipam.memoloop.repository;

import be.ipam.memoloop.model.StudiedCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudiedCardRepository extends JpaRepository<StudiedCard, Long> {

}