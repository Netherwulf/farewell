package farewell.authentication_module.models;


public enum UserType {
    USER("user"),
    EMPLOYEE("employee"),
    ADMIN("admin");

    public final String type;

    UserType(String type) {
        this.type = type;
    }
}