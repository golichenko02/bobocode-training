package com.bobocode.training;

import com.bobocode.data.Accounts;
import com.bobocode.training.hashtable.HashTable;

import java.time.LocalDate;

public class DemoApp {
    public static void main(String[] args) {
        var accounts = Accounts.generateAccountList(10);
        var emailToBirthdayTable = new HashTable<String, LocalDate>();
        accounts.forEach(a -> emailToBirthdayTable.put(a.getEmail(), a.getBirthday()));
        emailToBirthdayTable.printTable();
    }
}

