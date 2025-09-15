package org.mss.resource.mapper;

import org.mss.resource.entity.Mp3File;
import org.mss.resource.exception.InvalidFileFormatException;

public class Mp3FileMapper {

    public static Mp3File fromBytes(byte[] fileBytes) {
        if (fileBytes == null || fileBytes.length == 0) {
            throw new InvalidFileFormatException("Invalid MP3 file");
        }
        return Mp3File.builder()
                .data(fileBytes)
                .build();
    }
}