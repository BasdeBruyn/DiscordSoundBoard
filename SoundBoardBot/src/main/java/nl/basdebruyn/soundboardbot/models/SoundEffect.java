package nl.basdebruyn.soundboardbot.models;

public class SoundEffect {
    public String name;
    public String url;

    public SoundEffect() {}

    public SoundEffect(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return "SoundEffect{" +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
