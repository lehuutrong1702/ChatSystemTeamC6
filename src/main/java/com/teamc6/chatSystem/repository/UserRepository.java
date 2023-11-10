package com.teamc6.chatSystem.repository;

import com.teamc6.chatSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {
    //there are some method to access database, you can click on super class to see...
    //if you want to custom or add method you can use @Query annotation with JPQL syntax ...
    //For example: you want to create method to select User by username
    //Note that: the column name is the name of attribute in class not the name of column in database
    //Note that: the table name is the name of class not the name of schema in database
    @Query("SELECT u FROM User u Where u.username = :tendangnhap")
    User findByUsername(@Param("tendangnhap") String username);

    // ...
    //We can use also native SQL to define our query.
    // All we have to do is set the value of the nativeQuery attribute to true
    // and define the native SQL query in the value attribute of the annotation:
    // Example:
    // @Query( value = "SELECT u FROM user u Where u.username = :tendangnhap", nativeQuery = true )
    // User findByUsername(@Param("tendangnhap") String username);
    // Read document: "https://www.baeldung.com/spring-data-jpa-query"

}
