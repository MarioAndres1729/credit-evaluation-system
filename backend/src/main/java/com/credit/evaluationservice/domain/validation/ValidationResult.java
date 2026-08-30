package com.credit.evaluationservice.domain.validation;


public class ValidationResult {

    private final String validationName;
    private final boolean approved;
    private final String detail;

    public ValidationResult(
            String validationName,
            boolean approved,
            String detail) {
        this.validationName = validationName;
        this.approved = approved;
        this.detail = detail;
    }

    public String getValidationName() {
        return validationName;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getDetail() {
        return detail;
    }
}