package com.fp.api_rest.convertFiles;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.dao.PatientDAO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ManagerJDBC {

    private final PatientDAO patientDao;

    public ManagerJDBC(PatientDAO entidadDao) {
        this.patientDao = entidadDao;
    }

    @Transactional
    public void save(Patient patient) {
        patientDao.save(patient);
    }
}