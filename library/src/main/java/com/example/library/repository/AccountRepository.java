package com.example.library.repository;

import com.example.library.entity.Account;
import com.example.library.more.BookMore;
import com.example.library.more.Mess;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select account from Account account")
    public List<Account> getAllAccount(Pageable pageable);

    @Query("select account from  Account account where account.cardNumber = :cardNumber")
    public List<Account> findAllByCardNumber(@Param("cardNumber") Long cardNumber, Pageable pageable);

    @Query("select account from  Account account where account.name = :name")
    public List<Account> findAllByName(@Param("name") String name, Pageable pageable);

    @Query("select account from  Account account where account.email = :email")
    public List<Account> findAllByEmail(@Param("email") String email, Pageable pageable);

    @Query("select account from  Account account where account.phone = :phone")
    public List<Account> findAllByPhone(@Param("phone") Long phone, Pageable pageable);

    @Query("select account from  Account account where account.address = :address")
    public List<Account> findAllByAddress(@Param("address") String address, Pageable pageable);

    @Query("select account from  Account account where account.level = :level")
    public List<Account> findAllByLevel(@Param("level") int level, Pageable pageable);

    @Query("select account from  Account account where account.status = :status")
    public List<Account> findAllByStatus(@Param("status") String status, Pageable pageable);

    @Query("select account from Account account where account.status = 'opend' and account.email = :email and" +
            " account.password = SHA2(:password, 256)")
    public Account findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    public Account findFirstByCardNumber(Long cardNumber);

    @Query("select account from  Account account where account.status = 'opend' and account.level = 0")
    public List<Account> findAllByLowLevel();

    @Query("select new com.example.library.more.Mess(mainContent.type, mainContent.content, notification.sent, account.email)" +
            " from Account account inner join account.notificationsFromAccount notification" +
            " on account.cardNumber = notification.accountToNotification.cardNumber inner join notification.mainContentToNotification mainContent on" +
            " notification.mainContentToNotification.id = mainContent.id")
    public List<Mess> findAllMess(Pageable pageable);

    @Query("select new com.example.library.more.Mess(mainContent.type, mainContent.content, notification.sent, account.email)" +
            " from Account account inner join account.notificationsFromAccount notification" +
            " on account.cardNumber = notification.accountToNotification.cardNumber inner join notification.mainContentToNotification mainContent on" +
            " notification.mainContentToNotification.id = mainContent.id" +
            " where account.email = :email")
    public List<Mess> findAllMessByEmail(@Param("email") String email, Pageable pageable);

    @Query("select new com.example.library.more.Mess(mainContent.type, mainContent.content, notification.sent, account.email)" +
            " from Account account inner join account.notificationsFromAccount notification" +
            " on account.cardNumber = notification.accountToNotification.cardNumber inner join notification.mainContentToNotification mainContent on" +
            " notification.mainContentToNotification.id = mainContent.id" +
            " where mainContent.type = :type")
    public List<Mess> findAllMessByType(@Param("type") String type, Pageable pageable);

    @Query("select new com.example.library.more.Mess(mainContent.type, mainContent.content, notification.sent, account.email)" +
            " from Account account inner join account.notificationsFromAccount notification" +
            " on account.cardNumber = notification.accountToNotification.cardNumber inner join notification.mainContentToNotification mainContent on" +
            " notification.mainContentToNotification.id = mainContent.id" +
            " where mainContent.content = :content")
    public List<Mess> findAllMessByContent(@Param("content") String content, Pageable pageable);

    @Query("select account from Account account")
    public List<Account> findAllAccount();

}
