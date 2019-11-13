package com.revolut.banking.dao;

import com.revolut.banking.config.AppConstants;
import com.revolut.banking.config.H2Factory;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;
import com.revolut.banking.resources.BankingResource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankingAccountDaoImpl implements BankingAccountDao {

	static Logger log = Logger.getLogger(BankingResource.class.getName());

	private final Connection connection;
	private static final String GET_ACC = "SELECT * FROM BANKACCOUNT WHERE SSID = ?";
	private static final String NEW_ACC = "INSERT INTO BANKACCOUNT(SSID,BANKACCHOLDERNAME,BALANCE,EMAILID,CONTACT,ACCOUNTTYPE) VALUES(?,?,?,?,?,?)";
	private static final String DELETE_ACC = "DELETE FROM BANKACCOUNT WHERE SSID = ?";

	public BankingAccountDaoImpl() throws SQLException {
		this.connection = H2Factory.getConnection();
	}

	@Override
	public synchronized List<BankAccount> getAccounts(String SSID) throws GeneralBankingException {
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<BankAccount> accounts = new ArrayList<BankAccount>();
		String message = null;

		try {
			preparedStatement = this.connection.prepareStatement(GET_ACC);
			preparedStatement.setString(1, SSID);
			result = preparedStatement.executeQuery();

			while (result.next()) {
				BankAccount account = createAccountObjFromResult(result);
				accounts.add(account);
			}
		} catch (SQLException exception) {
			message = "Error while getting accounts details";
			log.error(message, exception);
			throw new GeneralBankingException(message);

		} finally {
			H2Factory.closeConnection();
			/*
			 * try { DatabaseInitialization.closeConnection(); } catch (SQLException e) {
			 * message = "Error while getting accounts details, closing connection";
			 * log.error(message, e); throw new GeneralBankingException(message); }
			 */
		}

		return accounts;
	}

	@Override
	public synchronized boolean createNewAccount(BankAccount account) throws GeneralBankingException {
		PreparedStatement preparedStatement = null;
		String message=null;

		try {
			preparedStatement = this.connection.prepareStatement(NEW_ACC);

			preparedStatement.setString(1, account.getSSID());
			preparedStatement.setString(2, account.getBankAccHolderName());
			preparedStatement.setBigDecimal(3, account.getBalance());
			preparedStatement.setString(4, account.getEmailId());
			preparedStatement.setString(5, account.getContact());
			preparedStatement.setString(6, account.getStrAccountType());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			message = "Error occured while creating account";
			log.error(message, e);
			throw new GeneralBankingException(message);
		}finally {
			H2Factory.closeConnection();
			/*
			 * try { DatabaseInitialization.closeConnection(); } catch (SQLException e) {
			 * message = "Error while creating accounts details, closing connection";
			 * log.error(message, e); throw new GeneralBankingException(message); }
			 */
		}

		return true;
	}

	private synchronized BankAccount createAccountObjFromResult(ResultSet resultSet) throws GeneralBankingException {
		BankAccount account = null;

		try {
			account = new BankAccount(resultSet.getInt("BANKACCID"), resultSet.getString("BANKACCHOLDERNAME"),
					resultSet.getBigDecimal("BALANCE"), resultSet.getString("CURRENCYCODE"),
					resultSet.getString("EMAILID"), resultSet.getString("SSID"), resultSet.getString("CONTACT"));

			account.setStrAccountType(resultSet.getString("ACCOUNTTYPE"));
			account.setStrStatus(resultSet.getString("STATUS"));
			account.setCreationDate(
					resultSet.getTimestamp("CREATE_DATE").toLocalDateTime().format(AppConstants.dateFormatter));
		} catch (SQLException e) {
			String message = "Error occurred while reading data from resultSet";
			log.error(message, e);
			throw new GeneralBankingException(message);
		}
		return account;
	}

	@Override
	public synchronized boolean deleteBankAccountsAsPerSSID(String SSID) throws GeneralBankingException {
		PreparedStatement preparedStatement = null;
		String message=null;
		
		try {
		preparedStatement = this.connection.prepareStatement(DELETE_ACC);
		preparedStatement.setString(1, SSID);
		preparedStatement.execute();
		} catch (SQLException e) {
			message = "Error occured while creating account";
			log.error(message, e);
			throw new GeneralBankingException(message);
		}finally {
			H2Factory.closeConnection();
			/*
			 * try { DatabaseInitialization.closeConnection(); } catch (SQLException e) {
			 * message = "Error while creating accounts details, closing connection";
			 * log.error(message, e); throw new GeneralBankingException(message); }
			 */
		}
		return true;
	}

}
