package nl.basdebruyn.soundboardbot.web.models;

public class SoundEffectRename {
    public final String oldName;
    public final String newName;

    public SoundEffectRename(String oldName, String newName) {
        this.oldName = oldName;
        this.newName = newName;
    }
}
