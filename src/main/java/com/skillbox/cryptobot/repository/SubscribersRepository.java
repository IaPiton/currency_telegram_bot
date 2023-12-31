package com.skillbox.cryptobot.repository;

import com.skillbox.cryptobot.model.Subscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SubscribersRepository extends JpaRepository<Subscribers, Integer> {
    boolean existsByUserId(Long userId);

    Subscribers findByUserId(Long userId);

    @Query(value = "Select s.price From Subscribers s where s.id = ?1", nativeQuery = true)
    Double findByPrise(Long userId);


}