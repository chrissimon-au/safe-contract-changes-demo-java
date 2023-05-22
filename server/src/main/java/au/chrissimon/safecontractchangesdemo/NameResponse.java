package au.chrissimon.safecontractchangesdemo;

import java.util.UUID;

public class NameResponse {
    private FullNameDto fullName;
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FullNameDto getFullName() {
        return fullName;
    }

    public void setFullName(FullNameDto fullName) {
        this.fullName = fullName;
    }

    public NameResponse() {
        super();
    }

    public NameResponse(UUID id, FullNameDto fullName) {
        super();
        this.id = id;
        this.fullName = fullName;
    }
}
