package org.mss.song.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mss.song.dto.MetadataRequestDto;
import org.mss.song.dto.MetadataResponseDto;
import org.mss.song.entity.Metadata;
import org.mss.song.mapper.MetadataDtoMapper;
import org.mss.song.service.MetadataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class MetadataController {
    private final static String ID = "id";
    private final static String IDS = "ids";
    private final MetadataService metadataService;
    private final MetadataDtoMapper metadataDtoMapper;

    @Transactional
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Map<String, String>> createSong(@Valid @RequestBody MetadataRequestDto data) {
        Metadata metadata = metadataDtoMapper.toEntity(data);
        Integer id = metadataService.save(metadata).getId();
        return ResponseEntity.ok()
                .body(Map.of(ID, Integer.toString(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetadataResponseDto> getSong(@PathVariable String id) {
        Metadata metadata = metadataService.getById(id);
        MetadataResponseDto responseDto = metadataDtoMapper.toResponseDto(metadata);
        return ResponseEntity.ok()
                .body(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Integer[]>> deleteSongs(@RequestParam("id") String ids) {
        List<Integer> deletedIds = metadataService.delete(ids);
        return ResponseEntity.ok()
                .body(Map.of(IDS, deletedIds.toArray(new Integer[0])));
    }
}