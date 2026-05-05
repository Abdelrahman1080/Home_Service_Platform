package org.example.userservice.ejb;

import jakarta.ejb.Stateful;
import jakarta.inject.Inject;
import org.example.userservice.entity.Wallet;
import org.example.userservice.repository.WalletRepository;

@Stateful
public class WalletBean {

    @Inject
    private WalletRepository repo;

    public void createWallet(Long userId, double balance) {
        repo.save(new Wallet(userId, balance));
    }

    public double getBalance(Long userId) {
        Wallet w = repo.findByUserId(userId);
        return w == null ? 0 : w.getBalance();
    }

    public String addFunds(Long userId, double amount) {
        Wallet w = repo.findByUserId(userId);
        if (w == null) return "wallet not found";
        w.setBalance(w.getBalance() + amount);
        repo.update(w);
        return "success";
    }

    public String deduct(Long userId, double amount) {
        Wallet w = repo.findByUserId(userId);
        if (w == null) return "wallet not found";
        if (w.getBalance() < amount) return "insufficient";
        w.setBalance(w.getBalance() - amount);
        repo.update(w);
        return "success";
    }
}
