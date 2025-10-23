package com.fp.api_rest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private Integer id;
    private String description;
    private Integer treatmentId;
    private Double totalAmount;
    private String issueDate;
    private Integer status;
}
