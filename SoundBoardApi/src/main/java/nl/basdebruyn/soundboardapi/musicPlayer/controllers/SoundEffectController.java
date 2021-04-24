package nl.basdebruyn.soundboardapi.musicPlayer.controllers;

import nl.basdebruyn.soundboardapi.auth.Roles;
import nl.basdebruyn.soundboardapi.musicPlayer.repositories.SoundEffectRepository;
import nl.basdebruyn.soundboardapi.musicPlayer.models.SoundEffect;
import nl.basdebruyn.soundboardapi.musicPlayer.models.SoundEffectRename;
import nl.basdebruyn.soundboardapi.web.statusErrors.NotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/sound_effect")
@Secured({Roles.WEB_USER, Roles.SERVICE})
public class SoundEffectController {
    public static final String SOUND_EFFECT_NOT_FOUND_MESSAGE = "No sound effect with name '%s' can be found";

    private final SoundEffectRepository soundEffectRepository;

    public SoundEffectController(SoundEffectRepository soundEffectRepository) {
        this.soundEffectRepository = soundEffectRepository;
    }

    @GetMapping
    public Iterable<SoundEffect> getAll() {
        return soundEffectRepository.findAll();
    }

    @GetMapping("/{name}")
    public SoundEffect getByName(@PathVariable String name) {
        Optional<SoundEffect> soundEffect = soundEffectRepository.findById(name);
        if (!soundEffect.isPresent()) {
            throw new NotFoundException(SOUND_EFFECT_NOT_FOUND_MESSAGE, name);
        }

        return soundEffect.get();
    }

    @GetMapping("/exists/{name}")
    public boolean exists(@PathVariable String name) {
        return soundEffectRepository.existsById(name);
    }

    @PostMapping
    public SoundEffect save(@Valid @RequestBody SoundEffect soundEffect) {
        return soundEffectRepository.save(soundEffect);
    }

    @PutMapping
    public SoundEffect update(@Valid @RequestBody SoundEffect soundEffect) {
        return soundEffectRepository.save(soundEffect);
    }

    @PostMapping("/rename")
    public SoundEffect rename(@Valid @RequestBody SoundEffectRename renameModel) {
        Optional<SoundEffect> currentSoundEffect = soundEffectRepository.findById(renameModel.oldName);
        if (!currentSoundEffect.isPresent()) {
            throw new NotFoundException(SOUND_EFFECT_NOT_FOUND_MESSAGE, renameModel.oldName);
        }

        soundEffectRepository.delete(currentSoundEffect.get());

        SoundEffect updatedSoundEffect = currentSoundEffect.get();
        updatedSoundEffect.name = renameModel.newName;

        return soundEffectRepository.save(updatedSoundEffect);
    }

    @Transactional
    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        Optional<SoundEffect> soundEffect = soundEffectRepository.findById(name);
        if (!soundEffect.isPresent()) {
            throw new NotFoundException(SOUND_EFFECT_NOT_FOUND_MESSAGE, name);
        }

        soundEffectRepository.delete(soundEffect.get());
    }
}
