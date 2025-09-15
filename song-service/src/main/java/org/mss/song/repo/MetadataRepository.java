package org.mss.song.repo;

import org.mss.song.entity.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface MetadataRepository extends JpaRepository<Metadata, Integer> {
}
