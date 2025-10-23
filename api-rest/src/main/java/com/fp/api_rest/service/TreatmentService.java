package com.fp.api_rest.service;
import com.fp.api_rest.model.Treatment;
import com.fp.api_rest.model.dao.TreatmentDAO;
import com.fp.api_rest.model.dto.TreatmentDTO;
import com.fp.api_rest.model.dto.mapper.TreatmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentService {

    @Autowired
    private final TreatmentDAO treatmentDAO;

    public TreatmentService(TreatmentDAO treatmentDAO) {
        this.treatmentDAO = treatmentDAO;
    }

    public List<TreatmentDTO> findAll() {
        return treatmentDAO.findAll()
                .stream()
                .map(TreatmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TreatmentDTO findById(Integer id) {
        return treatmentDAO.findById(id)
                .map(TreatmentMapper::toDTO)
                .orElse(null);
    }

    public TreatmentDTO save(TreatmentDTO dto) {
        Treatment treatment = TreatmentMapper.toEntity(dto);
        Treatment saved = treatmentDAO.save(treatment);
        return TreatmentMapper.toDTO(saved);
    }

    public void deleteById(Integer id) {
        treatmentDAO.deleteById(id);
    }


}
