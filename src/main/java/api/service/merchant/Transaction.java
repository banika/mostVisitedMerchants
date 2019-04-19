package api.service.merchant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Transaction {

    @JsonProperty("user-id")
    private int userId;
    private String merchant;

    public Transaction(int userId, String merchant, double price, Date date, int txId) {
        this.userId = userId;
        this.merchant = merchant;
        this.price = price;
        this.date = date;
        this.txId = txId;
    }


    public Transaction(){
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTxId() {
        return txId;
    }

    public void setTxId(int txId) {
        this.txId = txId;
    }

    private double price;

    @JsonProperty("purchase-date")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date date;
    @JsonProperty("tx-id")
    private int txId;
}
