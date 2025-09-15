package org.mss.resource.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mss.resource.service.Mp3FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/resources")
public class Mp3FileController {
    private final static String ID = "id";
    private final static String IDS = "ids";
    private final Mp3FileService mp3FileService;

    @PostMapping(consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, "audio/mpeg"})
    public ResponseEntity<Map<String, Integer>> createSong(@RequestBody(required = false) byte[] fileData) {
        Integer id = mp3FileService.save(fileData).getId();
        return ResponseEntity.ok()
                .body(Map.of("id", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getSong(@PathVariable String id) {
        byte[] audioData = mp3FileService.findById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("audio/mpeg"))
                .body(audioData);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Integer[]>> deleteSongs(@RequestParam("id") @Valid String ids) {
        Integer[] deletedIds = mp3FileService.delete(ids);
        return ResponseEntity.ok()
                .body(Map.of(IDS, deletedIds));
    }
}
