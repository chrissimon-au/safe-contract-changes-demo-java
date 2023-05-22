package au.chrissimon.safecontractchangesdemo;

public class TestAddNameRequest {
    private TestFullNameDto fullName;

    public TestFullNameDto getFullName() {
        return fullName;
    }

    public void setFullName(TestFullNameDto fullName) {
        this.fullName = fullName;
    }

    public TestAddNameRequest() {
        super();
    }

    public TestAddNameRequest(TestFullNameDto fullName) {
        super();
        this.fullName = fullName;
    }
}
