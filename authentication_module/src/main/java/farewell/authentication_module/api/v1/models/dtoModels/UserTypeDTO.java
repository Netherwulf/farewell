package farewell.authentication_module.api.v1.models.dtoModels;

public enum UserTypeDTO {
    USER("user"),
    EMPLOYEE("employee"),
    ADMIN("admin");

    public final String type;

    UserTypeDTO(String type) {
        this.type = type;
    }
}