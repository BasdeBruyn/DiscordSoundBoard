package nl.basdebruyn.soundboardapi.discord.models;

public class Guild {
    private String id;
    private String name;
    private String icon;

    public Guild() {
    }

    public Guild(String id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}
