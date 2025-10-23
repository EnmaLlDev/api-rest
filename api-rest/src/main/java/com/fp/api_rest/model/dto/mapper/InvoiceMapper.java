package com.fp.api_rest.model.dto.mapper;

import com.fp.api_rest.model.Invoice;
import com.fp.api_rest.model.Treatment;
import com.fp.api_rest.model.dto.InvoiceDTO;
import com.fp.api_rest.model.dto.TreatmentDTO;

public class InvoiceMapper {
    public static InvoiceDTO toDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(invoice.getId());
        dto.setDescription(invoice.getDescription());
        dto.setTreatmentId(invoice.getTreatmentId() != null ?
                invoice.getTreatmentId().getId() : 0);
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setIssueDate(invoice.getIssueDate().toString());
        dto.setStatus(invoice.getStatus());
        return dto;
    }

    public static Invoice toEntity(InvoiceDTO dto) {
        Invoice invoice = new Invoice();
        invoice.setId(dto.getId());
        invoice.setDescription(dto.getDescription());
        // fetch Treatment by its ID
        Treatment treatment = new Treatment();
            treatment.setId(dto.getTreatmentId());
            invoice.setTreatmentId(treatment);
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setIssueDate(java.time.LocalDateTime.parse(dto.getIssueDate()));
        invoice.setStatus(dto.getStatus());
        return invoice;
    }
}
