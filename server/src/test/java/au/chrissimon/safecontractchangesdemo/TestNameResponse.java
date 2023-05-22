package au.chrissimon.safecontractchangesdemo;

import java.util.UUID;

public class TestNameResponse {
    private String name;
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

    public TestNameResponse() {
        super();
    }

    public TestNameResponse(UUID id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}
