package url.genchi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import url.genchi.async.AsyncService;
import url.genchi.mongo.BankAccount;
import url.genchi.mongo.Repository;
import com.google.common.util.concurrent.Striped;

import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;

@RestController
@SpringBootApplication
@EnableAsync
public class Application {

    @Autowired
    AsyncService asyncService;

    @Autowired
    Repository repository;

    Striped<Lock> striped = Striped.lazyWeakLock(1);

    @RequestMapping(value = "/async", method = RequestMethod.GET)
    public String asyncFun() throws Exception{
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        Future<String> page1 = asyncService.findUser("PivotalSoftware");
        Future<String> page2 = asyncService.findUser("CloudFoundry");
        Future<String> page3 = asyncService.findUser("Spring-Projects");

        // Wait until they are all done
        while (!(page1.isDone() && page2.isDone() && page3.isDone())) {
            Thread.sleep(10); //10-millisecond pause between each check
        }

        // Print results, including elapsed time
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
        System.out.println(page1.get());
        System.out.println(page2.get());
        System.out.println(page3.get());
        return "total time: " + Long.toString(System.currentTimeMillis() - start) + "ms";
    }

    @RequestMapping(value = "/unsecure/debit")
    public String unsecureDebit(@RequestParam("name") String name, @RequestParam("debit") int debit) {
        BankAccount bankAccount = repository.findByName(name);
        if(bankAccount.getAmount() > debit) {
            bankAccount.setAmount(bankAccount.getAmount() - debit);
            repository.save(bankAccount);
        }
        return "now amount: " + bankAccount.getAmount();
    }

    @RequestMapping(value = "/secure/debit")
    public String secureDebit(@RequestParam("name") String name, @RequestParam("debit") int debit) {
        Lock lock = striped.get(name);
        lock.lock();
        BankAccount bankAccount = repository.findByName(name);
        if(bankAccount.getAmount() > debit) {
            bankAccount.setAmount(bankAccount.getAmount() - debit);
            repository.save(bankAccount);
        }
        lock.unlock();
        return "now amount: " + bankAccount.getAmount();
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        Repository repository = ctx.getBean(Repository.class);
        BankAccount bankAccount = new BankAccount("G7", 100);
        repository.save(bankAccount);
    }
}
