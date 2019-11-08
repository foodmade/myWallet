package com.mywallet.repository;

import com.mywallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author linapex
 */
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    /**
     * 根据用户id查询钱包
     *
     * @param uid
     * @return
     */
    List<Wallet> findByUid(@Param(value = "uid") Integer uid);

    /**
     * 根据地址查询钱包
     * @param address
     * @return
     */
    Wallet findByAddress(@Param(value = "address") String address);
}

