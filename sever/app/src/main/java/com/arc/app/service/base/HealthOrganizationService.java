package com.arc.app.service.base;

import com.arc.app.request.base.HealthOrgRequest;
import com.arc.app.request.search.SearchRequest;
import com.arc.app.response.ResponseObject;
import org.springframework.data.domain.Page;

import java.io.InputStream;

public interface HealthOrganizationService {
    ResponseObject find(Long id);
    Boolean duplicate(String code);
    ResponseObject save(HealthOrgRequest request);
    Boolean delete(Long id);
    ResponseObject setLocked(Long id);
    Page<HealthOrgRequest> getPage(SearchRequest request);
    ResponseObject saveListData(InputStream inputStream);
}
