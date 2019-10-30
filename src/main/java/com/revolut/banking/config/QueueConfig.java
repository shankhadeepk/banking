package com.revolut.banking.config;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.revolut.banking.model.BankTransaction;
import com.revolut.banking.resources.BankingResource;

public class QueueConfig {
	static Logger log = Logger.getLogger(QueueConfig.class.getName());

	private static final BlockingQueue<BankTransaction> allBankTransactions = new LinkedBlockingQueue<>();

	public void addTransactions(BankTransaction transaction) {
		if (transaction.getTransactionId() != null) {
			try {
				allBankTransactions.put(transaction);
			} catch (InterruptedException e) {
				log.error("Error occurred while adding data to Queue", e);
			}
		}
	}

	public void processTransactions() {
		while (allBankTransactions.isEmpty()) {
			try {
				BankTransaction bankTransaction = allBankTransactions.take();
			} catch (InterruptedException e) {
				log.error("Error occurred while reading data from Queue", e);
			}
		}

	}

}
