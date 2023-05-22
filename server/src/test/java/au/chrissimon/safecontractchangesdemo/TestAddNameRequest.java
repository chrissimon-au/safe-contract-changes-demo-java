package au.chrissimon.safecontractchangesdemo;

public class TestAddNameRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestAddNameRequest() {
        super();
    }

    public TestAddNameRequest(String name) {
        super();
        this.name = name;
    }
}
