package com.incapp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incapp.entity.NewsCheck;
import com.incapp.entity.UserAccount;

@Repository
public interface NewsRepo extends JpaRepository<NewsCheck, Long> {

	List<NewsCheck> findByUserOrderByCreatedAtDesc(UserAccount user);
}
