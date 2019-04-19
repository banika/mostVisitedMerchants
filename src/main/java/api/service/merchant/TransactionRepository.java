package api.service.merchant;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
public class TransactionRepository {

    List<Transaction> transactions = new ArrayList<>();
    Map<Integer, User> userMap = new HashMap<>();

    public Map<Integer, User> getUserMap() {
        return userMap;
    }

    public void save(Transaction newTransaction) {

        Integer userId = newTransaction.getUserId();
        String merchant = newTransaction.getMerchant();


        if (!userMap.containsKey(userId)) {
            User user = new User(userId);
            user.visit(merchant);
            userMap.put(userId, user);
        } else {
            User user = userMap.get(userId);
            user.visit(merchant);
        }

        transactions.add(newTransaction);
    }

    public List<String> findByUserId(Integer userId) throws Exception {
        if(userMap.get(userId)!=null)
            return userMap.get(userId).mostVisited(3);
        else
            throw new UserNotFoundException();
    }

    public void loadData() {

        List<Transaction> transactions = null;
        Pattern pattern = Pattern.compile(",");

        try (BufferedReader in = new BufferedReader(new FileReader(ResourceUtils.getFile("classpath:coding_challenge_data.csv")))) {
            transactions = in
                    .lines()
                    .map(line -> {
                        String[] x = pattern.split(line);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date date;
                        try {
                            date = format.parse(x[4]);
                        } catch (Exception ParseException) {
                            date = new Date();
                        }
                        return new Transaction(Integer.parseInt(x[0]),
                                x[1],
                                Double.parseDouble(x[3]),
                                date,
                                Integer.parseInt(x[5]));
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        for (Transaction transaction : transactions) {
            save(transaction);
        }
    }
}

