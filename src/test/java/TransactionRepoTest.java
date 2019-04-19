import api.service.merchant.TooFewTransactionsException;
import api.service.merchant.Transaction;
import api.service.merchant.TransactionRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;

public class TransactionRepoTest {

    @Test
    public void testIfTransactionGetsSaved() {
        TransactionRepository repo = new TransactionRepository();

        Assert.assertEquals(0,repo.getUserMap().size());
        Date date = new Date();
        Transaction transaction = new Transaction(1, "Bartell Drugs", 1.1,date,1);
        repo.save(transaction);
        Assert.assertEquals(1,repo.getUserMap().size());
    }


    @Test
    public void testIfUsersRecordIsFound() throws Exception {
        TransactionRepository repo = new TransactionRepository();

        Date date = new Date();
        Transaction transaction = new Transaction(1, "Bartell Drugs1", 1.1,date,1);
        repo.save(transaction);

        transaction = new Transaction(1, "Bartell Drugs1", 1.1,date,1);
        repo.save(transaction);

        transaction = new Transaction(1, "Bartell Drugs2", 1.1,date,1);
        repo.save(transaction);

        transaction = new Transaction(1, "Bartell Drugs3", 1.1,date,1);
        repo.save(transaction);

        transaction = new Transaction(1, "Bartell Drugs1", 1.1,date,1);
        repo.save(transaction);

        transaction = new Transaction(1, "Bartell Drugs2", 1.1,date,1);
        repo.save(transaction);

        transaction = new Transaction(1, "Bartell Drugs4", 1.1,date,1);
        repo.save(transaction);

        transaction = new Transaction(1, "Bartell Drugs4", 1.1,date,1);
        repo.save(transaction);


        List<String> merchants = repo.findByUserId(1);

        Assert.assertEquals(3,merchants.size());

        List<String> expected = Arrays.asList("Bartell Drugs1", "Bartell Drugs2", "Bartell Drugs4");

        Assert.assertThat("List equality without order",
                merchants, containsInAnyOrder(expected.toArray()));

    }

    @Test(expected = TooFewTransactionsException.class)
    public void testWhenTransactionsAreNotEnough() throws Exception {
        TransactionRepository repo = new TransactionRepository();

        Date date = new Date();
        Transaction transaction = new Transaction(1, "Bartell Drugs1", 1.1, date, 1);
        repo.save(transaction);

        repo.findByUserId(1);
    }

    @Test(expected = TooFewTransactionsException.class)
    public void testWhenMerchantNumberIsLess() throws Exception {
        TransactionRepository repo = new TransactionRepository();

        Date date = new Date();
        Transaction transaction = new Transaction(1, "Bartell Drugs1", 1.1, date, 1);
        repo.save(transaction);

        transaction = new Transaction(1, "Bartell Drugs1", 1.1, date, 1);
        repo.save(transaction);

        transaction = new Transaction(1, "Bartell Drugs1", 1.1, date, 1);
        repo.save(transaction);

        transaction = new Transaction(1, "Bartell Drugs1", 1.1, date, 1);
        repo.save(transaction);

        transaction = new Transaction(1, "Bartell Drugs1", 1.1, date, 1);
        repo.save(transaction);

        repo.findByUserId(1);
    }

    @Test
    public void testForMultipleUsers() throws Exception{
        TransactionRepository repo = new TransactionRepository();

        Date date = new Date();
        Transaction transaction = new Transaction(1, "Bartell Drugs", 1.1,date,1);
        repo.save(transaction);

        transaction = new Transaction(1, "Flying Pie Pizza", 1.1,date,1);
        repo.save(transaction);

        transaction = new Transaction(1, "Bartell Drugs", 1.1,date,1);
        repo.save(transaction);

        transaction = new Transaction(1, "Safeway", 1.1, date, 1);
        repo.save(transaction);

        transaction = new Transaction(1, "Flying Pie Pizza", 1.1, date, 1);
        repo.save(transaction);

        transaction = new Transaction(1, "Safeway", 1.1,date, 1);
        repo.save(transaction);

        transaction = new Transaction(2, "Acme Inc", 1.1,date, 1);
        repo.save(transaction);

        transaction = new Transaction(2, "Bartell Drugs", 1.1,date, 1);
        repo.save(transaction);

        transaction = new Transaction(2, "Albertsons", 1.1,date, 1);
        repo.save(transaction);

        transaction = new Transaction(2, "Bartell Drugs", 1.1,date, 1);
        repo.save(transaction);

        transaction = new Transaction(2, "Albertsons", 1.1, date, 1);
        repo.save(transaction);


        transaction = new Transaction(2, "Albertsons", 1.1, date, 1);
        repo.save(transaction);

        List<String> merchantListForUser1 = repo.findByUserId(1);
        List<String> merchantListForUser2 = repo.findByUserId(2);


        List<String> expectedForUser1 = Arrays.asList("Bartell Drugs", "Flying Pie Pizza", "Safeway");
        List<String> expectedForUser2 = Arrays.asList("Bartell Drugs", "Albertsons", "Acme Inc");

        Assert.assertThat("List equality without order",
                merchantListForUser1, containsInAnyOrder(expectedForUser1.toArray()));

        Assert.assertThat("List equality without order",
                merchantListForUser2, containsInAnyOrder(expectedForUser2.toArray()));

    }
}
