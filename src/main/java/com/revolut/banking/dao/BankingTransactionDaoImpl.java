package com.revolut.banking.dao;

import com.revolut.banking.config.AppConstants;
import com.revolut.banking.config.H2DatabaseFactory;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankingTransactionnResponse;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankingTransactionDaoImpl implements BankingTransactionDao {
	
	static Logger log = Logger.getLogger(BankingTransactionDaoImpl.class.getName());

	private static final String GET_A_TRANSACT = "SELECT * FROM BANKTRANSACTION WHERE TRANSACTIONID=?";
	private static final String GET_TRANSACTIONS = "SELECT * FROM BANKTRANSACTION";
	private static final String NEW_TRANSACT = "INSERT INTO BANKTRANSACTION(TRANSACTIONID,TYPEOFTRANSACTION,FROMACCOUNT,TOACCOUNT,FROMACCHOLDERNAME,TOACCHOLDERNAME) "
			+ "VALUES(?,?,?,?,?,?)";
	private static final String DELETE_TRANSACT = "DELETE FROM BANKTRANSACTION WHERE TRANSACTIONID=?";


	@Override
	public synchronized BankingTransactionnResponse saveTransaction(BankingTransactionnResponse transaction) throws GeneralBankingException {
		PreparedStatement preparedStatement = null;
		String message=null;
		List<BankingTransactionnResponse> transactions=null;
		Connection connection=null;

		try {
			connection=H2DatabaseFactory.getConnection();
			String transactionId=transaction.getTransactionId();
			preparedStatement = connection.prepareStatement(NEW_TRANSACT);

			preparedStatement.setString(1, transaction.getTransactionId());
			preparedStatement.setString(2, transaction.getTypeOfTransaction());
			preparedStatement.setLong(3, transaction.getFromAccount());
			preparedStatement.setLong(4, transaction.getToAccount());
			preparedStatement.setString(5, transaction.getFromAccHolderName());
			preparedStatement.setString(6, transaction.getToAccountHolderName());
			preparedStatement.executeUpdate();

			transactions=getTransactions(transactionId);

		} catch (SQLException e) {
			message = "Error occured while creating transaction";
			log.error(message, e);
			throw new GeneralBankingException(message);
		}finally {
			DbUtils.closeQuietly(preparedStatement);
			DbUtils.closeQuietly(connection);
		}

		if(transactions!=null)
			return transactions.get(0);
		else
			return null;
	}
	
	public synchronized List<BankingTransactionnResponse> getTransactions(String transactionId) throws GeneralBankingException {
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<BankingTransactionnResponse> transactions = new ArrayList<>();
		String message = null;
		Connection connection=null;

		try {
			connection=H2DatabaseFactory.getConnection();
			if(transactionId!=null) {
				preparedStatement = connection.prepareStatement(GET_A_TRANSACT);
				preparedStatement.setString(1,transactionId);
			}else {
				preparedStatement = connection.prepareStatement(GET_TRANSACTIONS);
			}
			result = preparedStatement.executeQuery();

			while (result.next()) {
				BankingTransactionnResponse transaction = createTransactionFromResult(result);
				transactions.add(transaction);
			}
		} catch (SQLException exception) {
			message = "Error while getting accounts details";
			log.error(message, exception);
			throw new GeneralBankingException(message);

		} finally {
			DbUtils.closeQuietly(preparedStatement);
			DbUtils.closeQuietly(connection);
		}

		return transactions;
	}
	
	private synchronized BankingTransactionnResponse createTransactionFromResult(ResultSet resultSet) throws GeneralBankingException {
		BankingTransactionnResponse transaction = null;

		try {
			transaction = new BankingTransactionnResponse(resultSet.getString("TRANSACTIONID"), resultSet.getString("TYPEOFTRANSACTION"),
					resultSet.getLong("FROMACCOUNT"), resultSet.getLong("TOACCOUNT"),
					resultSet.getString("FROMACCHOLDERNAME"), resultSet.getString("TOACCHOLDERNAME"));
			
			if(resultSet.getTimestamp("CREATE_DATE")!=null)
				transaction.setDateOfCreation(
					resultSet.getTimestamp("CREATE_DATE").toLocalDateTime().format(AppConstants.dateFormatter));
			
			if(resultSet.getTimestamp("MODIFY_DATE")!=null)
				transaction.setDateOfModification(
					resultSet.getTimestamp("MODIFY_DATE").toLocalDateTime().format(AppConstants.dateFormatter));
		} catch (SQLException e) {
			String message = "Error occurred while reading data from resultSet";
			log.error(message, e);
			throw new GeneralBankingException(message);
		}
		return transaction;
	}

}
