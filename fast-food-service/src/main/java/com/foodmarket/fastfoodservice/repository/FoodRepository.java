package com.foodmarket.fastfoodservice.repository;


import com.foodmarket.fastfoodservice.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query("select e from Food e WHERE e.name  = :name")
    List<Food> findByNameQuery(@Param("name") String name);

    @Query("select e from Food e where UPPER(e.name) like upper(concat('%', :name, '%')) ")
    List<Food> findAllByLike(@Param("name") String name);

    List<Food> findAllByCategory_Id(@Param("category") Long categoryId);
}


