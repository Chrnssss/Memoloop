package be.ipam.memoloop.service;

import be.ipam.memoloop.dto.StudiedCardDto;
import be.ipam.memoloop.mapper.StudiedCardMapper;
import be.ipam.memoloop.repository.StudiedCardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StudiedCardService {
    private final StudiedCardRepository studiedCardRepository;
    private final StudiedCardMapper studiedCardMapper;

    public StudiedCardService(StudiedCardRepository studiedCardRepository, StudiedCardMapper studiedCardMapper) {
        this.studiedCardRepository = studiedCardRepository;
        this.studiedCardMapper = studiedCardMapper;
    }

    //update card
    public StudiedCardDto updateStudiedCard(StudiedCardDto studiedCardDto) {
        var studiedCard = studiedCardRepository.findById(studiedCardDto.getId())
                .orElseThrow(() -> new RuntimeException("Studied card not found"));
        studiedCard.setLastStudiedAt(LocalDateTime.now());
        studiedCard.setViews(studiedCard.getViews()+1);
        studiedCard.setKnowledgeLevel(studiedCardDto.getKnowledgeLevel());
        return studiedCardMapper.toDto(studiedCardRepository.save(studiedCard));
    }
}
