package com.credit.evaluationservice.domain.repository;

public interface BlacklistRepository {

    boolean isBlocked(String documentNumber);
}
