package com.fp.api_rest.repository.dao.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@ConditionalOnProperty(name = "app.data.source", havingValue = "jdbc")
@Repository
public class PatientImplDAO {
}
