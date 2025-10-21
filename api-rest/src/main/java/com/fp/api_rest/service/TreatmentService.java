package com.fp.api_rest.service;

import com.fp.api_rest.model.Treatment;
import com.fp.api_rest.repository.TreatmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TreatmentService {

    private final TreatmentRepository treatmentRepository;

    public TreatmentService(TreatmentRepository treatmentRepository) {this.treatmentRepository = treatmentRepository;}

    @Transactional(readOnly = true)
    public Treatment save(Treatment treatment) {return treatmentRepository.save(treatment);}

    @Transactional(readOnly = true)
    public List<Treatment> getAllTreatments() {
        return treatmentRepository.findAll();
    }

    @Transactional
    public void deleteTreatment(int id) {
        treatmentRepository.deleteById(id);
    }

    @Transactional
    public Treatment updateTreatment(Treatment treatment) {
        if (treatmentRepository.existsById(treatment.getId())) {
            return treatmentRepository.save(treatment);
        } else {
            throw new IllegalArgumentException("Doctor with id " + treatment.getId() + " does not exist.");
        }
    }
}
