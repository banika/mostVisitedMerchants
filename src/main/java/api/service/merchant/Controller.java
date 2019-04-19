package api.service.merchant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
public class Controller {

    @Autowired
    private TransactionRepository repo;

    @PostMapping("/transaction")
    void newTransaction(@RequestBody Transaction newTransaction) {
        System.out.println(newTransaction);
        repo.save(newTransaction);
    }

    @GetMapping("/user/{id}")
    List<String> getRecord(@PathVariable Integer id) throws TooFewTransactionsException{
        return repo.findByUserId(id);
    }

    @PostConstruct
    public void initData() {
        repo.loadData();
    }
}

