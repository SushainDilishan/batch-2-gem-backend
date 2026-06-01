package com.srilankagem.gembackend.dealer.service;

import com.srilankagem.gembackend.dealer.dto.DealerResponse;
import com.srilankagem.gembackend.dealer.dto.UpdateDealerRequest;
import com.srilankagem.gembackend.dealer.entity.Dealer;
import com.srilankagem.gembackend.dealer.entity.DealerTier;
import com.srilankagem.gembackend.dealer.repository.DealerRepository;
import com.srilankagem.gembackend.dealer.service.DealerService;
import com.srilankagem.gembackend.dealer.dto.PagedResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class  DealerService {

    private final DealerRepository dealerRepository;


    public DealerResponse getDealer(Long dealerId) {

        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Dealer not found with id: " + dealerId));

        return mapToResponse(dealer);
    }


    public PagedResponse<DealerResponse> getAllDealers(
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<Dealer> dealerPage = dealerRepository.findAll(pageable);

        return buildPagedResponse(dealerPage);
    }


    public PagedResponse<DealerResponse> getDealersByTier(
            DealerTier tier,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<Dealer> dealerPage =
                dealerRepository.findByTier(tier, pageable);

        return buildPagedResponse(dealerPage);
    }


    public DealerResponse updateDealer(
            Long dealerId,
            UpdateDealerRequest request
    ) {

        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Dealer not found with id: " + dealerId));

        dealer.setContactPerson(request.getContactPerson());
        dealer.setEmail(request.getEmail());
        dealer.setCountry(request.getCountry());
        dealer.setShippingAddress(request.getShippingAddress());
        dealer.setTier(request.getTier());

        Dealer updatedDealer = dealerRepository.save(dealer);

        return mapToResponse(updatedDealer);
    }

    private DealerResponse mapToResponse(Dealer dealer) {

        return DealerResponse.builder()
                .id(dealer.getId())
                .contactPerson(dealer.getContactPerson())
                .email(dealer.getEmail())
                .country(dealer.getCountry())
                .shippingAddress(dealer.getShippingAddress())
                .tier(dealer.getTier())
                .createdAt(dealer.getCreatedAt())
                .updatedAt(dealer.getUpdatedAt())
                .build();
    }

    private PagedResponse<DealerResponse> buildPagedResponse(
            Page<Dealer> dealerPage
    ) {

        List<DealerResponse> content =
                dealerPage.getContent()
                        .stream()
                        .map(this::mapToResponse)
                        .toList();

        return PagedResponse.<DealerResponse>builder()
                .content(content)
                .page(dealerPage.getNumber())
                .size(dealerPage.getSize())
                .totalElements(dealerPage.getTotalElements())
                .totalPages(dealerPage.getTotalPages())
                .first(dealerPage.isFirst())
                .last(dealerPage.isLast())
                .build();
    }
}