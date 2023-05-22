package au.chrissimon.safecontractchangesdemo;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Name {
    private @Id UUID id;
    private String name;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Name() {
        super();
    }

    public Name(String name) {
        super();
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public NameResponse toResponse() {
        return new NameResponse(id, name);
    }
}
