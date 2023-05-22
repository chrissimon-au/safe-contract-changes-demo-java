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

    public Name(FullNameDto fullName) {
        super();
        this.id = UUID.randomUUID();
        this.name = (fullName.getFirstName() + " " + fullName.getLastName()).trim();
    }

    public NameResponse toResponse() {
        String[] names = name.split("\\s", 2);
        String lastName = names.length > 1 ? names[1] : "";
        return new NameResponse(id, new FullNameDto(names[0], lastName));
    }
}
