package au.chrissimon.safecontractchangesdemo;

public class TestAddNameRequest {
    private TestFullName fullName;

    public TestFullName getFullName() {
        return fullName;
    }

    public void setFullName(TestFullName fullName) {
        this.fullName = fullName;
    }

    public TestAddNameRequest() {
        super();
    }

    public TestAddNameRequest(TestFullName fullName) {
        super();
        this.fullName = fullName;
    }
}
