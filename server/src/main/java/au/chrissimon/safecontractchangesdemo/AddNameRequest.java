package au.chrissimon.safecontractchangesdemo;

public class AddNameRequest {
    private String name;
    private FullNameDto fullName;

    public FullNameDto getFullName() {
        return fullName;
    }

    public void setFullName(FullNameDto fullName) {
        this.fullName = fullName;
        if (fullName != null) {
            this.name = fullName.getFirstName() + " " + fullName.getLastName();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddNameRequest() {
        super();
    }

    public AddNameRequest(String name) {
        super();
        this.name = name;
    }
}
