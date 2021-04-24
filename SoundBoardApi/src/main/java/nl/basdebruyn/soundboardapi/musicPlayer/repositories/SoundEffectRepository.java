package nl.basdebruyn.soundboardapi.musicPlayer.repositories;

import nl.basdebruyn.soundboardapi.musicPlayer.models.SoundEffect;
import org.springframework.data.repository.CrudRepository;

public interface SoundEffectRepository extends CrudRepository<SoundEffect, String> {
}
