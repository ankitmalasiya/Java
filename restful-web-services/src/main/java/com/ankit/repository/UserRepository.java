package com.ankit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ankit.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findById(Long id);

	User deleteUserById(Long id);

	List<User> findByUserNameContaining(String value);

	@Query("SELECT a FROM User a WHERE a.userName LIKE %:value%")
	List<User> findByUserNameLike(@Param("value") String value);

}
