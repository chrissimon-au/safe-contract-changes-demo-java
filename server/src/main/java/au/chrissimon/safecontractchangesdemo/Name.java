package au.chrissimon.safecontractchangesdemo;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Name {
    private @Id UUID id;
    private String name;
    private FullName fullName;

    public FullName getFullName() {
        return fullName;
    }

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
        this.fullName = FullName.fromDto(fullName);
        this.name = this.fullName.toString();
    }

    public NameResponse toResponse() {
        if (fullName == null || fullName.getFirstName() == null || fullName.getLastName() == null) {
            String[] names = name.split("\\s", 2);
            String lastName = names.length > 1 ? names[1] : "";
            return new NameResponse(id, new FullNameDto(names[0], lastName));
        }
        return new NameResponse(id, fullName.asDto());
    }
}
