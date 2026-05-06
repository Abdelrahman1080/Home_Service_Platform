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



    public boolean deduct(Long userId, Double amount) {
        Double balance = getBalance(userId);

        if (balance < amount)
            return false;

        addFunds(userId, -amount);

        return true;
    }

    public void refund(Long userId, Double amount) {
        addFunds(userId, amount);
    }
}
