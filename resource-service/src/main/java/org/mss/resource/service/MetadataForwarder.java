package org.mss.resource.service;

import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.mss.resource.dto.MetadataDto;
import org.mss.resource.entity.Mp3File;
import org.mss.resource.mapper.MetadataMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class MetadataForwarder {
    @Value("${song.service.uri}")
    private String SONG_SERVICE_URI;
    private final RestTemplate restTemplate;

    public void sendMetadata(Mp3File mp3File) {
        Metadata metadata = mp3File.getMetadata();
        MetadataDto dto = MetadataMapper.mapToMetadataDto(metadata);
        dto.setId(mp3File.getId());
        try {
            restTemplate.postForEntity(SONG_SERVICE_URI, dto, Void.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to forward metadata to Song Service" + e.getMessage());
        }
    }

    public void deleteMetadata(String id) {
        try {
            String url = UriComponentsBuilder.fromUriString(SONG_SERVICE_URI)
                    .queryParam("id", id)
                    .toUriString();
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Failed to forward metadata to Song Service");
        }
    }
}
