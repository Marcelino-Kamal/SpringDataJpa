package com.tutorial.tutorial.account;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
/* tansactional is All or nothing aka if transfer money fail it won't update the database and it will rollback 
    this is important as without it it will update the database and this is dangerous
     and work on public method 
 */
@Transactional
public class AccountServices {

    private final AccountRepo aRepo;

    public AccountServices(AccountRepo aRepo) {
        this.aRepo = aRepo;
    }


    public void transfer(Account from ,Account to , BigDecimal amount){
        from.setbalance(from.getbalance().subtract(amount));
        aRepo.save(from);

        if(true){
            //used to rollback in sql
            throw new IllegalStateException("can't set balance to account: " + to.getId());
        }
        to.setbalance(to.getbalance().add(amount));
        aRepo.save(to);
    }
}
