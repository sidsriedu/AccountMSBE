package com.cg.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entities.WalletUser;

public interface WalletUserRepo extends JpaRepository<WalletUser, Integer> {

}
