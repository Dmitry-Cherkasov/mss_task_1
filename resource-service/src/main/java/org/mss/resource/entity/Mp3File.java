package org.mss.resource.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.tika.metadata.Metadata;
import org.hibernate.annotations.JdbcTypeCode;
import org.mss.resource.util.MetadataExtractor;

import java.sql.Types;

@Entity
@Table(name = "mss_media_files")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Mp3File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @JdbcTypeCode(Types.BINARY)
    @Column(name = "data", columnDefinition = "BYTEA")
    private byte[] data;

    public Metadata getMetadata() {
        return MetadataExtractor.extractMetadata(data);
    }
}
