package com.xuandanh.ems.repository;

import com.xuandanh.ems.domain.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {
    List<WishList> findAllByUserIdOrderByCreatedDateDesc(Integer userId);
}
