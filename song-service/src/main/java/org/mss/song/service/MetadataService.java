package org.mss.song.service;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mss.song.entity.Metadata;
import org.mss.song.exception.*;
import org.mss.song.repo.MetadataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetadataService {
    private final MetadataRepository metadataRepository;

    @Transactional
    public Metadata save(Metadata metadata) {
        try {
            if (metadataRepository.existsById(metadata.getId())) {
                throw new PersistenceConflictException(metadata.getId().toString());
            }
            return metadataRepository.save(metadata);
        } catch (PersistenceException exception) {
            throw new DatabaseException("Failed to save metadata");
        }
    }

    public Metadata getById(String id) {
        int idValue = parseAndValidateIds(id).get(0);
        try {
            return metadataRepository.findById(idValue)
                    .orElseThrow(() -> new FileNotFoundException(id));
        } catch (PersistenceException exception) {
            throw new DatabaseException("Failed to retrieve data");
        }
    }

    public List<Integer> delete(String rawIds) {
        List<Integer> ids = parseAndValidateIds(rawIds);
        List<Integer> deletedIds = new ArrayList<>();
        for (Integer id : ids) {
            try {
                if (metadataRepository.existsById(id)) {
                    metadataRepository.deleteById(id);
                    deletedIds.add(id);
                }
            } catch (PersistenceException exception) {
                throw new DatabaseException("Server side error");
            }
        }
        return deletedIds;
    }

    private List<Integer> parseAndValidateIds(String inputString) {
        if (inputString.length() > 200) {
            throw new IdLengthException(String.valueOf(inputString.length()));
        }
        String[] tokens = inputString.split(",");
        List<Integer> ids = new ArrayList<>();
        for (String token : tokens) {
            try {
                int id = Integer.parseInt(token);
                if (id <= 0) throw new InvalidIdException(token);
                ids.add(id);
            } catch (NumberFormatException e) {
                throw new InvalidIdException(String.valueOf(token));
            }
        }
        return ids;
    }
}
