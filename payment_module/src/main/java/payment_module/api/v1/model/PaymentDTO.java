package payment_module.api.v1.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;

    @NotBlank
    private Long amount;

    @NotBlank
    private boolean reservation;

    @NotBlank
    private boolean purchase;

    @NotBlank
    private boolean grave;

    @NotBlank
    private boolean funeral;

    private Long userId;
}
