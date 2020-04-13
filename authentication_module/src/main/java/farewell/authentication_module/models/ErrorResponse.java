package farewell.authentication_module.models;

import lombok.Data;

@Data
public class ErrorResponse {

    private final Integer code;
    private final String message;

    public ErrorResponse(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
