package au.chrissimon.safecontractchangesdemo;

import java.util.UUID;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Name {
    private @Id UUID id;
    @Embedded private FullName name;

    public UUID getId() {
        return id;
    }

    public FullName getName() {
        return name;
    }

    public Name() {
        super();
    }

    public Name(FullName name) {
        super();
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public NameResponse toResponse() {
        return new NameResponse(id, name.toResponse());
    }
}
