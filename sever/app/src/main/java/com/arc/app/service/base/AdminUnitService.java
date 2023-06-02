package com.arc.app.service.base;

import com.arc.app.request.base.AdminUnitRequest;
import com.arc.app.request.search.SearchRequest;
import com.arc.app.response.ResponseObject;
import org.springframework.data.domain.Page;

import java.io.InputStream;

public interface AdminUnitService {
    ResponseObject find(Long id);
    Boolean duplicate(String code);
    ResponseObject save(AdminUnitRequest request);
    Boolean delete(Long id);
    ResponseObject setLocked(Long id);
    Page<AdminUnitRequest> getPage(SearchRequest request);
    ResponseObject saveListData(InputStream inputStream);
}
