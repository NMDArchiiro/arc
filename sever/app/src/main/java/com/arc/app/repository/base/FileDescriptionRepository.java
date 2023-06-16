package com.arc.app.repository.base;

import com.arc.app.entity.base.FileDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author: NMDuc
 **/
@Repository
public interface FileDescriptionRepository extends JpaRepository<FileDescription, Long> {
}
