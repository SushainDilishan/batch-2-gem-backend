package com.srilankagem.gembackend.gem.dto;

import com.srilankagem.gembackend.gem.models.GemOrigin;
import com.srilankagem.gembackend.gem.models.GemTreatment;
import com.srilankagem.gembackend.gem.models.GemType;
import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GemStoneRequest {

    @NotBlank(message = "Gem code is required")
    @Size(min = 3, max = 30, message = "gem code must be between 3 to 30 characters")
    private String gemCode;

    @NotNull(message = "Gem type is required")
    private GemType type;

    @NotBlank(message = "color is required")
    private String color;

    @NotNull
    @DecimalMin(value = "0.01", message = "Value must be greater than 0.01")
    private Double caratWeight;

    @NotNull(message = "origin is required")
    private GemOrigin origin;

    @NotNull(message = "gem treatment is required")
    private GemTreatment treatment;

    @NotNull
    @DecimalMin(value = "0.01", message = "Value has to be greater than 0.01")
    private Double pricePerCarat;

    @NotNull
    @Min(value = 0, message = "Value has to be greater than 0")
    private Integer stockQuantity;

    @NotBlank
    @Size(max = 1000)
    private String description;

    private boolean certified;

}
