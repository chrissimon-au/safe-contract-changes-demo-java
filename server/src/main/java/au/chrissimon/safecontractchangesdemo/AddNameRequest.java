package au.chrissimon.safecontractchangesdemo;

public class AddNameRequest {
    private FullNameDto fullName;

    public FullNameDto getFullName() {
        return fullName;
    }

    public void setFullName(FullNameDto fullName) {
        this.fullName = fullName;
    }

    public AddNameRequest() {
        super();
    }
}
