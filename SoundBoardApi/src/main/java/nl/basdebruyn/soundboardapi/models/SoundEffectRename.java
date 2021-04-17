package nl.basdebruyn.soundboardapi.models;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class SoundEffectRename {
    @NotNull
    @Length(max = 20)
    public final String oldName;

    @NotNull
    @Length(max = 20)
    public final String newName;

    public SoundEffectRename(String oldName, String newName) {
        this.oldName = oldName;
        this.newName = newName;
    }
}
