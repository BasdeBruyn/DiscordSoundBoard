package nl.basdebruyn.soundboardapi.controllers;

import nl.basdebruyn.soundboardapi.models.SoundEffect;
import nl.basdebruyn.soundboardapi.models.SoundEffectRename;
import nl.basdebruyn.soundboardapi.repositories.SoundEffectRepository;
import nl.basdebruyn.soundboardapi.statusErrors.NotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/sound_effect")
public class SoundEffectController {
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
        return soundEffectRepository.findById(name).orElseThrow(NotFoundException::new);
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
        SoundEffect soundEffect = soundEffectRepository.findById(renameModel.oldName).orElseThrow(NotFoundException::new);
        soundEffectRepository.delete(soundEffect);

        soundEffect.name = renameModel.newName;
        return soundEffectRepository.save(soundEffect);
    }

    @DeleteMapping("/{name}")
    @Transactional
    public void delete(@PathVariable String name) {
        soundEffectRepository.deleteById(name);
    }
}
