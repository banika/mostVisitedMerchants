package api.service.merchant;


import java.util.*;

public class User {


    private PriorityQueue<Merchant> record;
    private Map<String, Merchant> merchantMap;
    private static final int MINIMUM_TRANSACTION = 5;

    private int userId;
    private int totalTransactions;

    class Merchant implements Comparable<Merchant>{
        private String name;
        private int visitCount;

        public Merchant(String name) {
            this.name = name;
            this.visitCount = 0;
        }

        void visited() {
            this.visitCount++;
        }
        @Override
        public int compareTo(Merchant that) {
            return that.visitCount-this.visitCount;
        }
    }

    void commitTransaction() {
        this.totalTransactions++;
    }

    public User(int userId) {
        this.userId = userId;
        this.totalTransactions = 1;
        this.record = new PriorityQueue<>();
        this.merchantMap = new HashMap<>();
    }

    void visit(String merchantName) {
        if (!this.merchantMap.containsKey(merchantName)) {
            this.merchantMap.put(merchantName, new Merchant(merchantName));
            Merchant merchant = this.merchantMap.get(merchantName);
            merchant.visited();
            this.record.add(merchant);
        } else {
            Merchant merchant = this.merchantMap.get(merchantName);
            this.record.remove(merchant);
            merchant.visited();
            this.record.add(merchant);
        }
        commitTransaction();
    }

    List<String> mostVisited(int n) throws TooFewTransactionsException{

        if(this.totalTransactions<MINIMUM_TRANSACTION){
            throw new TooFewTransactionsException("Too few transaction for the user:"+this.userId);
        }

        Object[] merchants = this.record.toArray();
        Arrays.sort(merchants);
        List<String> list = new ArrayList<>();

        if(merchants.length<n)
            throw new TooFewTransactionsException("Too few transaction for the user:"+this.userId);

        for(int i=0;i<n;i++){
            list.add(((Merchant)merchants[i]).name);
        }

        return list;
    }
}