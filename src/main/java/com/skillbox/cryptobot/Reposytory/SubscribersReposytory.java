package com.skillbox.cryptobot.Reposytory;

import com.skillbox.cryptobot.Model.Subscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribersReposytory extends JpaRepository<Subscribers, Integer> {
    boolean existsByUserId(Long userId);

    Subscribers findByUserId(Long userId);
}
