package au.chrissimon.safecontractchangesdemo;

public class TestAddNameRequest {
    private String name;
    private TestFullNameDto fullName;

    public TestFullNameDto getFullName() {
        return fullName;
    }

    public void setFullName(TestFullNameDto fullName) {
        this.fullName = fullName;
    }

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

    public TestAddNameRequest(TestFullNameDto fullName) {
        super();
        this.fullName = fullName;
    }
}
