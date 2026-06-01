package com.srilankagem.gembackend.dealer.repository;

import com.srilankagem.gembackend.dealer.entity.Dealer;
import com.srilankagem.gembackend.dealer.entity.DealerTier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealerRepository extends JpaRepository<Dealer, Long> {

    Page<Dealer> findByTier(DealerTier tier, Pageable pageable);

}
