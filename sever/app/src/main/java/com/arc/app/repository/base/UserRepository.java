package com.arc.app.repository.base;

import com.arc.app.entity.base.User;
import com.arc.app.request.base.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select count(e) from User e where e.username like :username")
    Long countDuplicate(String username);

    @Query(value = "select new com.arc.app.request.base.UserSecurity(e) from User e where e.username like :username")
    UserSecurity findByUsername(String username);
}
