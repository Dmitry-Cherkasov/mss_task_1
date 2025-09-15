package org.mss.resource.mapper;

import org.apache.tika.metadata.Metadata;
import org.mss.resource.dto.MetadataDto;

import java.util.HashMap;
import java.util.Map;

public class MetadataMapper {

    public static MetadataDto mapToMetadataDto(Metadata metadata) {
        Map<String, String> metadataMap = new HashMap<>();
        for (String key : metadata.names()) {
            String[] values = metadata.getValues(key);
                metadataMap.put(cleanAttribute(key), values[0]);
        }
        return MetadataDto.builder()
                .name(metadataMap.get("title"))
                .album(metadataMap.get("album"))
                .artist(metadataMap.get("creator"))
                .duration(transformDuration(metadataMap.get("duration")))
                .year(metadataMap.get("releaseDate"))
                .build();
    }

    private static String cleanAttribute(String string) {
        String pattern = "xmpDM:|dc:";
        return string.replaceAll(pattern, "");
    }

    private static String transformDuration(String duration) {
        try {
            double durationDouble = Double.parseDouble(duration);
            int durationInt = (int) Math.round(durationDouble);
            return String.format("%02d:%02d", durationInt / 60, durationInt % 60);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid duration format: " + duration, e);
        }
    }
}
