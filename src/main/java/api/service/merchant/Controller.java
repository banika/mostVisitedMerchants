package api.service.merchant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    List<String> getRecord(@PathVariable Integer id) throws Exception{
        try {
            return repo.findByUserId(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User Not Found", ex);
        }
    }

    @PostConstruct
    public void initData() {
        repo.loadData();
    }
}

