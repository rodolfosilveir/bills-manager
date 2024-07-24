package br.com.totvs.bills_manager.adapter.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totvs.bills_manager.adapter.out.persistence.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>{

    Optional<UserEntity> findByLogin(String login);
    
}