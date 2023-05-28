package com.arc.app.service;

import com.arc.app.request.HealthOrganizationRequest;
import com.arc.app.request.search.SearchRequest;
import com.arc.app.response.ResponseObject;
import org.springframework.data.domain.Page;

import java.io.InputStream;

public interface HealthOrganizationService {
    ResponseObject find(Long id);
    Boolean duplicate(String code);
    ResponseObject save(HealthOrganizationRequest request);
    Boolean delete(Long id);
    ResponseObject setLocked(Long id);
    Page<HealthOrganizationRequest> getPage(SearchRequest request);
    ResponseObject saveListData(InputStream inputStream);
}
