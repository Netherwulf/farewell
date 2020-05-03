package farewell.authentication_module.api.v1.models.dtoModels;

import farewell.authentication_module.models.UserType;

public enum UserTypeDTO {
    USER("user"),
    EMPLOYEE("employee"),
    ADMIN("admin");

    public final String type;

    UserTypeDTO(String type) {
        this.type = type;
    }
}