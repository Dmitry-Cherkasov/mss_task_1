package org.mss.song.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MetadataRequestDto {
    @NotNull(message = "Id is required")
    @Positive(message = "Id must be positive")
    private Integer id;

    @NotBlank(message = "Song name is required")
    @Size(max = 100, message = "Name can contain 1-100 characters")
    private String name;

    @NotBlank(message = "Artist is required")
    @Size(max = 100, message = "Artist can contain 1-100 characters")
    private String artist;

    @NotBlank(message = "Album is required")
    @Size(max = 100, message = "Album can contain 1-100 characters")
    private String album;

    @NotBlank(message = "Duration is required")
    @Pattern(
            regexp = "^(?:[0-5][0-9]):[0-5][0-9]$",
            message = "Duration must be in mm:ss format with leading zeros"
    )
    private String duration;

    @NotBlank(message = "Year is required")
    @Pattern(
            regexp = "^(19\\d{2}|20\\d{2})$",
            message = "Year must be between 1900 and 2099"
    )
    private String year;
}
