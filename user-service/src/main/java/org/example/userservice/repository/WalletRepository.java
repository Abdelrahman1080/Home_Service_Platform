package org.example.userservice.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import org.example.userservice.entity.Wallet;

import java.util.List;

@Stateless
public class WalletRepository {

    @PersistenceContext(unitName = "userPU")
    private EntityManager em;

    public void save(Wallet wallet) {
        em.persist(wallet);
    }

    public Wallet findByUserId(Long userId) {
        List<Wallet> wallets = em.createQuery("SELECT w FROM Wallet w WHERE w.userId = :uid", Wallet.class)
                .setParameter("uid", userId)
                .getResultList();

        return wallets.isEmpty() ? null : wallets.get(0);
    }

    public void update(Wallet wallet) {
        em.merge(wallet);
    }
}
