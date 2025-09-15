package org.mss.resource.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class MetadataDto {
    private Integer id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private String year;
}
