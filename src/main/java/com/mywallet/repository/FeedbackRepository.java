package com.mywallet.repository;

import com.mywallet.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author linapex
 * @Date 21:25 2018/2/3
 */
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
//    @Query("from Person p where p.name =:name")
//    List<Person> findByName(@Param(value = "name") String name);
}

