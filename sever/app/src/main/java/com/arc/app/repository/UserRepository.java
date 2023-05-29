package com.arc.app.repository;

import com.arc.app.entity.User;
import com.arc.app.request.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select count(e) from User e where e.username like :username")
    Long countDuplicate(String username);

    @Query(value = "select new com.arc.app.request.UserSecurity(e) from User e where e.username like :username")
    UserSecurity findByUsername(String username);
}
