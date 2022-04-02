package com.lkh.exam.jpaBoard.user.dao;

import com.lkh.exam.jpaBoard.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {
}
