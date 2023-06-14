package com.arc.app.repository.base;

import com.arc.app.utils.ConvertUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Map;

/**
 * author: NMDuc
 **/
@Repository
public abstract class BaseRepository {
    protected static final String FORMATDATETIME = "yyyy-MM-dd hh:mm:ss";
    protected static final String FORMATDATE = "yyyy-MM-dd";

    @PersistenceContext
    private EntityManager entityManager;

    protected Query query(String fileName, Map<String, Object> params) {
        if (!fileName.endsWith(".sql")) {
            fileName = fileName + ".sql";
        }
        String queryStr = ConvertUtils.readFile("static/report/sql/" + fileName);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if(queryStr != null) {
                    queryStr = queryStr.replaceAll(String.format("@%s", entry.getKey()), String.format(":%s", entry.getKey()));
                }
            }
        }
        Query query = getQuery(queryStr);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query;
    }

    protected Query getQuery(String queryStr) {
        return entityManager.createNativeQuery(queryStr);
    }

    protected Query getQuery(String queryStr, Class type) {
        return entityManager.createNativeQuery(queryStr, type);
    }
}
