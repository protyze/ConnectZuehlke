package ch.zuehlke.fullstack.ConnectZuehlke.domain;

public class Capability {
    private String id;
    private String name;

    public Capability(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
