package org.mss.resource.repo;

import org.mss.resource.entity.Mp3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface Mp3FileRepository extends JpaRepository<Mp3File, Integer> {
}
