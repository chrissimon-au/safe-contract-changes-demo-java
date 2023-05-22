package au.chrissimon.safecontractchangesdemo;

public class AddNameRequest {
    private String name;

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
