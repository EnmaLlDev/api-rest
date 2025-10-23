package com.fp.api_rest.controller;
import com.fp.api_rest.model.dto.InvoiceDTO;
import com.fp.api_rest.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/getAll")
    public List<InvoiceDTO> getAllInvoices() { return invoiceService.findAll(); }

    @GetMapping("/{id}")
    public InvoiceDTO getInvoiceById(@PathVariable Integer id) {
        return invoiceService.findById(id);
    }

    @PostMapping
    public InvoiceDTO createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.save(invoiceDTO);
    }

    @PutMapping
    public InvoiceDTO updateInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.save(invoiceDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Integer id) {
        invoiceService.deleteById(id);
    }

    @GetMapping("/status/{status} ")
    public List<InvoiceDTO> getInvoicesByStatus(@PathVariable Integer status) {
        return invoiceService.getInvoicesByStatus(status);
    }
}
