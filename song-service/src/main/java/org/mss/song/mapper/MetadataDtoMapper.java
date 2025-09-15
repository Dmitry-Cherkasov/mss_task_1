package org.mss.song.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mss.song.dto.MetadataRequestDto;
import org.mss.song.dto.MetadataResponseDto;
import org.mss.song.entity.Metadata;


@Mapper(componentModel = "spring")
public interface MetadataDtoMapper {

    @Mapping(target = "duration", expression = "java(parseDuration(dto.getDuration()))")
    @Mapping(target = "year", expression = "java(Integer.parseInt(dto.getYear()))")
    Metadata toEntity(MetadataRequestDto dto);

    @Mapping(target = "duration", expression = "java(toPattern(entity.getDuration()))")
    MetadataResponseDto toResponseDto(Metadata entity);

    default int parseDuration(String duration) {
        String[] parts = duration.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    default String toPattern(int duration) {
        return String.format("%02d:%02d", duration / 60, duration % 60);
    }
}