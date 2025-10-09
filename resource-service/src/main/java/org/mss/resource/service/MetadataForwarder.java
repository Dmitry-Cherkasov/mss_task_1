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
    @Value("${song.service.endpoint}")
    private String ENDPOINT_PATH;
    @Value("${song.service.name}")
    private String SONG_SERVICE_NAME;
    private final RestTemplate restTemplate;

    public void sendMetadata(Mp3File mp3File) {
        Metadata metadata = mp3File.getMetadata();
        MetadataDto dto = MetadataMapper.mapToMetadataDto(metadata);
        dto.setId(mp3File.getId());
        try {
            restTemplate.postForEntity(getUrl(), dto, Void.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to forward metadata to Song Service" + e.getMessage());
        }
    }

    public void deleteMetadata(String id) {
        try {
            String url = UriComponentsBuilder.fromUriString(getUrl())
                    .queryParam("id", id)
                    .toUriString();
            restTemplate.delete(url);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve service");
        }
    }

    private String getUrl() {
        return "http://" + SONG_SERVICE_NAME + ENDPOINT_PATH;
    }
}
