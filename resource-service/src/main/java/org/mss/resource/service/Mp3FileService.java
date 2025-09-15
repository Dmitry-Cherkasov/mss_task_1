package org.mss.resource.service;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mss.resource.exception.InvalidIdException;
import org.mss.resource.exception.NotValidIdLength;
import org.mss.resource.repo.Mp3FileRepository;
import org.mss.resource.entity.Mp3File;
import org.mss.resource.exception.FileNotFoundException;
import org.mss.resource.exception.DatabaseException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Mp3FileService {
    private final Mp3FileRepository mp3FileRepository;
    private final MetadataForwarder metadataForwarder;

    @Transactional
    public Mp3File save(byte[] file) {
        Mp3File mp3File = Mp3File.builder()
                .data(file)
                .build();
        try {
            mp3FileRepository.save(mp3File);
            metadataForwarder.sendMetadata(mp3File);
            return mp3File;
        } catch (Exception exception) {
            throw new DatabaseException("Failed to save file");
        }
    }

    public byte[] findById(String idString) {
        int id = parseAndValidateIds(idString).get(0);
        try {
            Mp3File file = mp3FileRepository.findById(id).orElseThrow(() -> new FileNotFoundException(String.valueOf(id)));
            return file.getData();
        } catch (PersistenceException exception) {
            throw new DatabaseException("Failed to retrieve data");
        }
    }

    public Integer[] delete(String string) {
        List<Integer> ids = parseAndValidateIds(string);
        List<Integer> deletedIds = new ArrayList<>();
        for (int id : ids) {
            try {
                if (mp3FileRepository.existsById(id)) {
                    mp3FileRepository.deleteById(id);
                    deletedIds.add(id);
                    metadataForwarder.deleteMetadata(string);
                }
            } catch (PersistenceException exception) {
                throw new DatabaseException("Server side error");
            }
        }
        return deletedIds.toArray(new Integer[0]);
    }

    private List<Integer> parseAndValidateIds(String inputString) {
        if (inputString.length() > 200) {
            throw new NotValidIdLength(String.valueOf(inputString.length()));
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
