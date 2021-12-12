package com.foodmarket.fastfoodservice.repository;

import com.foodmarket.fastfoodservice.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select e from Category e WHERE e.name = :name")
    List<Category> findByNameQuery(@Param("name") String name);

    @Query("select e from Category e where UPPER(e.name) like upper(concat('%', :name, '%'))")
    List<Category> findAllByLike(@Param("name") String name);

}
