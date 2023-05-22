package au.chrissimon.safecontractchangesdemo;

import jakarta.persistence.Embeddable;

@Embeddable
public class FullName {
    private String firstName;
    public String getFirstName() {
        return firstName;
    }
    private String lastName;
    public String getLastName() {
        return lastName;
    }
    public FullName() {
        super();
    }

    public static FullName fromDto(FullNameDto fullNameDto) {
        return new FullName(fullNameDto.getFirstName(), fullNameDto.getLastName());
    }

    public FullNameDto asDto() {
        return new FullNameDto(firstName, lastName);
    }

    public FullName(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public FullNameDto toResponse() {
        return new FullNameDto(getFirstName(), getLastName());
    }

    public String toString() {
        return (getFirstName() + " " + getLastName()).trim();
    }
}