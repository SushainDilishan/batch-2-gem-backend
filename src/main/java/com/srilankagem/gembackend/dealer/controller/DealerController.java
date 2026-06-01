package com.srilankagem.gembackend.dealer.controller;

import com.srilankagem.gembackend.dealer.dto.PagedResponse;
import com.srilankagem.gembackend.dealer.dto.DealerResponse;
import com.srilankagem.gembackend.dealer.dto.UpdateDealerRequest;
import com.srilankagem.gembackend.dealer.entity.DealerTier;
import com.srilankagem.gembackend.dealer.service.DealerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dealers")
@RequiredArgsConstructor
@Validated
public class DealerController {

    private final DealerService dealerService;

    @GetMapping("/{dealerId}")
    public DealerResponse getDealer(
            @PathVariable
            @Positive(message = "Dealer ID must be greater than zero")
            Long dealerId
    ) {
        return dealerService.getDealer(dealerId);
    }

    @GetMapping
    public PagedResponse<DealerResponse> getAllDealers(

            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Page number cannot be negative")
            int page,

            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "Page size must be at least 1")
            @Max(value = 100, message = "Page size cannot exceed 100")
            int size
    ) {

        return dealerService.getAllDealers(page, size);
    }

    @GetMapping("/tier/{tier}")
    public PagedResponse<DealerResponse> getDealersByTier(

            @PathVariable
            DealerTier tier,

            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Page number cannot be negative")
            int page,

            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "Page size must be at least 1")
            @Max(value = 100, message = "Page size cannot exceed 100")
            int size
    ) {

        return dealerService.getDealersByTier(
                tier,
                page,
                size
        );
    }

    @PutMapping("/{dealerId}")
    public DealerResponse updateDealer(

            @PathVariable
            @Positive(message = "Dealer ID must be greater than zero")
            Long dealerId,

            @Valid
            @RequestBody
            UpdateDealerRequest request
    ) {

        return dealerService.updateDealer(
                dealerId,
                request
        );
    }
}