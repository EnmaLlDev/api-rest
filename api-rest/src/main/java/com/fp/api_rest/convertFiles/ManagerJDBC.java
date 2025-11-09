package com.fp.api_rest.convertFiles;
import com.fp.api_rest.model.Patient;
import com.fp.api_rest.repository.dao.PatientDAO;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("jdbc")
public class ManagerJDBC {

    private final PatientDAO patientDao;

    public ManagerJDBC(PatientDAO patientDao) {
        this.patientDao = patientDao;
    }

    @Transactional
    public void save(Patient patient) {
        patientDao.save(patient);
    }
}