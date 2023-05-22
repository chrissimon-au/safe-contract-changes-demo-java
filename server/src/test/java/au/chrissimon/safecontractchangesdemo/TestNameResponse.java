package au.chrissimon.safecontractchangesdemo;

import java.util.UUID;

public class TestNameResponse {
    private TestFullName fullName;
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TestFullName getFullName() {
        return fullName;
    }

    public void setFullName(TestFullName fullName) {
        this.fullName = fullName;
    }

    public TestNameResponse() {
        super();
    }

    public TestNameResponse(UUID id, TestFullName fullName) {
        super();
        this.id = id;
        this.fullName = fullName;
    }
}
