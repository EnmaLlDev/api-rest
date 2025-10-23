package com.fp.api_rest.service;

import com.fp.api_rest.model.Invoice;
import com.fp.api_rest.model.dao.InvoiceDAO;
import com.fp.api_rest.model.dto.InvoiceDTO;
import com.fp.api_rest.model.dto.mapper.InvoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class InvoiceService {

    @Autowired
    private final InvoiceDAO invoiceDAO;

    public InvoiceService(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    public List<InvoiceDTO> findAll() {
        return invoiceDAO.findAll()
                .stream()
                .map(InvoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public InvoiceDTO findById(Integer id) {
        return invoiceDAO.findById(id)
                .map(InvoiceMapper::toDTO)
                .orElse(null);
    }

    public InvoiceDTO save(InvoiceDTO dto) {
        Invoice invoice = new Invoice();
        Invoice saved = invoiceDAO.save(invoice);
        return  InvoiceMapper.toDTO(saved);
    }

    public void deleteById(Integer id) { invoiceDAO.deleteById(id); }

    public List<InvoiceDTO> getInvoicesByStatus(Integer status) {
        return invoiceDAO.getInvoicesByStatus(status)
                .stream()
                .map(InvoiceMapper::toDTO)
                .collect(Collectors.toList());
    }
}
