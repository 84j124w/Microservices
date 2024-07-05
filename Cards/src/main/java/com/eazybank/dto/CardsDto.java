package com.eazybank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
        name = "Cards",
        description = "Schema to hold Card Information"
)
@Data
public class CardsDto {

    @Schema(
            description = "Mobile number of Customer", example = "9876543210"
    )
    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "Mobile number must be 10 digit")
    private String mobileNumber;

    @Schema(
            description = "Card number of the Customer", example = "1025453637"
    )
    @NotEmpty(message = "Card number cannot be null or empty")
    @Pattern(regexp ="^$|[0-9]{12}", message = "Card number must be 12 digit")
    private String cardNumber;

    @Schema(
            description = "Type of the Card", example = "Credit Card"
    )
    @NotEmpty(message = "Card type cannot be null or empty")
    private String cardType;

    @Schema(
            description = "Total amount limit available against a Card", example = "100000"
    )
    @Positive(message = "Total card limit should be greater than zero")
    private int totalLimit;

    @Schema(
            description = "Total amount used by a Customer", example = "1000"
    )
    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    private int amountUsed;

    @Schema(
            description = "Total available amount against a Card", example = "90000"
    )
    @PositiveOrZero(message = "Available amount should be equal or greater than zero")
    private int availableAmount;
}
