package au.chrissimon.safecontractchangesdemo;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Name {
    private @Id UUID id;
    private FullName fullName;

    public FullName getFullName() {
        return fullName;
    }

    public UUID getId() {
        return id;
    }

    public Name() {
        super();
    }

    public Name(FullNameDto fullName) {
        super();
        this.id = UUID.randomUUID();
        this.fullName = FullName.fromDto(fullName);
    }

    public NameResponse toResponse() {
        return new NameResponse(id, fullName.asDto());
    }
}
