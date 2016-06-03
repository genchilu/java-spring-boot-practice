package url.genchi.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by mac on 2016/6/3.
 */
@Document(collection = "BankAccount")
public class BankAccount {
    @Id
    private String name;
    private int amount;
    public BankAccount(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getAmount() {
        return this.amount;
    }
    public void setAmount(int amount){
        this.amount = amount;
    }
    public String toString() {
        return "{name: " + this.name + ", amount: " + this.amount + "}";
    }
}
