package com.xuandanh.ems.repository;

import com.xuandanh.ems.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Categoryrepository extends JpaRepository<Category, Integer> {

    Category findByCategoryName(String categoryName);

}