package com.credit.evaluationservice.infrastructure;

import java.util.Set;
import org.springframework.stereotype.Repository;

import com.credit.evaluationservice.domain.repository.BlacklistRepository;

@Repository 
public class InMemoryBlacklistRepository implements BlacklistRepository {

    private final Set<String> blockedDocuments = Set.of(
            "1111111111",
            "2222222222",
            "3333333333",
            "4444444444",
            "5555555555"
    );

    @Override
    public boolean isBlocked(String documentNumber) {
        return blockedDocuments.contains(documentNumber);
    }
}
