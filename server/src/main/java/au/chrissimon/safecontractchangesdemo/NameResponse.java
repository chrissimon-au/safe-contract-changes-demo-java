package au.chrissimon.safecontractchangesdemo;

import java.util.UUID;

public class NameResponse {
    private String name;
    private FullNameDto fullName;
    public FullNameDto getFullName() {
        return fullName;
    }

    public void setFullName(FullNameDto fullName) {
        this.fullName = fullName;
    }

    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NameResponse() {
        super();
    }

    public NameResponse(UUID id, String name) {
        super();
        this.id = id;
        this.name = name;
        String[] names = name.split("\\s", 2);
        String lastName = names.length > 1 ? names[1] : "";
        this.fullName = new FullNameDto(names[0], lastName);
    }
}
