package com.fp.api_rest.model.dao;

import com.fp.api_rest.model.Invoice;
import com.fp.api_rest.model.dao.base.BaseDAO;

import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceDAO extends BaseDAO<Invoice, Integer> {
    List<Invoice> getInvoicesByStatus(Integer status);
    List<Invoice> getInvoicesByIssueDate(LocalDateTime issueDate);
}
