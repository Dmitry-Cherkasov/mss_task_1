package org.mss.song.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.mss.song.validator.CsvMaxLength;
import org.mss.song.validator.ValidCsvIds;

import java.util.Arrays;

@Data
public class DeleteRequestDto {

    @NotBlank(message = "ID list must not be empty")
    @ValidCsvIds
    @CsvMaxLength(max = 200)
    private String ids;

    public Integer[] getIdArray() {
        return Arrays.stream(ids.split(","))
                .map(Integer::valueOf)
                .toArray(Integer[]::new);
    }
}
