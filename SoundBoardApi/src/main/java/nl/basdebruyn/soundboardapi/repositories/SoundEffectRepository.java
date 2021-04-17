package nl.basdebruyn.soundboardapi.repositories;

import nl.basdebruyn.soundboardapi.models.SoundEffect;
import org.springframework.data.repository.CrudRepository;

public interface SoundEffectRepository extends CrudRepository<SoundEffect, String> {
}
