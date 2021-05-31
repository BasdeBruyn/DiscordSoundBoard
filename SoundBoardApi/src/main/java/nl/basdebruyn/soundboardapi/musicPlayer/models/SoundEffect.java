package nl.basdebruyn.soundboardapi.musicPlayer.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class SoundEffect {
    @Id
    @NotNull
    @Length(max = 20)
    public String name;

    @NotNull
    @Length(max = 50)
    public String url;

    public SoundEffect() {}

    public SoundEffect(String name, String url) {
        this.name = name;
        this.url = url;
    }
}

