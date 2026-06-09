package be.ipam.memoloop.controller;

import be.ipam.memoloop.dto.StudiedCardDto;
import be.ipam.memoloop.service.StudiedCardService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/studiedcard")
class StudiedCardController {
    private final StudiedCardService studiedCardService;

    public StudiedCardController(StudiedCardService studiedCardService) {
        this.studiedCardService = studiedCardService;
    }

    //Put studied card
    @PutMapping
    public StudiedCardDto updateStudiedCard(@RequestBody StudiedCardDto studiedCardDto) {
        return studiedCardService.updateStudiedCard(studiedCardDto);
    }
}
