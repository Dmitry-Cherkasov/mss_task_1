package org.mss.song.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class MetadataResponseDto {
    private Integer id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private int year;
}
