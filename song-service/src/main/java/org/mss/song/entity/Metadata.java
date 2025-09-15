package org.mss.song.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mss_metadata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {
    @Id
    Integer id;
    private String name;
    private String artist;
    private String album;
    private int duration;
    private int year;
}
