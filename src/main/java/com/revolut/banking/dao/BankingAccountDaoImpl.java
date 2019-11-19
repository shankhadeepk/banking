package com.revolut.banking.dao;

import com.revolut.banking.config.AppConstants;
import com.revolut.banking.config.H2DatabaseFactory;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.model.BankAccount;
import com.revolut.banking.resources.BankingResource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankingAccountDaoImpl implements BankingAccountDao {

	static Logger log = Logger.getLogger(BankingResource.class.getName());

	private static final String GET_ACC_SSID = "SELECT * FROM BANKACCOUNT WHERE SSID = ?";
	private static final String GET_ACC_ACCID = "SELECT * FROM BANKACCOUNT WHERE  BANKACCID= ?";
	private static final String NEW_ACC = "INSERT INTO BANKACCOUNT(SSID,BANKACCHOLDERNAME,BALANCE,EMAILID,CONTACT,ACCOUNTTYPE) VALUES(?,?,?,?,?,?)";
	private static final String DELETE_ACC_SSID = "DELETE FROM BANKACCOUNT WHERE SSID = ?";
	private static final String DELETE_ACC_ACCID = "DELETE FROM BANKACCOUNT WHERE BANKACCID = ?";
	private static final String UPDATE_ACC="UPDATE BANKACCOUNT SET BALANCE = ? WHERE BANKACCID = ?";


	@Override
	public synchronized List<BankAccount> getAccounts(String SSID) throws GeneralBankingException {
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<BankAccount> accounts = new ArrayList<BankAccount>();
		String message = null;
		Connection connection=null;

		try {
			connection=H2DatabaseFactory.getConnection();
			preparedStatement = connection.prepareStatement(GET_ACC_SSID);
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
			DbUtils.closeQuietly(preparedStatement);
			DbUtils.closeQuietly(connection);
		}

		return accounts;
	}

	@Override
	public synchronized boolean createNewAccount(BankAccount account) throws GeneralBankingException {
		PreparedStatement preparedStatement = null;
		String message=null;
		Connection connection=null;

		try {
			connection=H2DatabaseFactory.getConnection();
			preparedStatement = connection.prepareStatement(NEW_ACC);

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
			DbUtils.closeQuietly(preparedStatement);
			DbUtils.closeQuietly(connection);
		}

		return true;
	}
	@Override
	public synchronized boolean deleteBankAccountsAsPerSSID(String SSID) throws GeneralBankingException {
		PreparedStatement preparedStatement = null;
		String message=null;
		Connection connection=null;

		try {
			connection=H2DatabaseFactory.getConnection();
			preparedStatement = connection.prepareStatement(DELETE_ACC_SSID);
			preparedStatement.setString(1, SSID);
			preparedStatement.execute();
		} catch (SQLException e) {
			message = "Error occured while creating account";
			log.error(message, e);
			throw new GeneralBankingException(message);
		}finally {
			DbUtils.closeQuietly(preparedStatement);
			DbUtils.closeQuietly(connection);
		}
		return true;
	}

	@Override
	public synchronized int deleteBankAccountsAsPerAccountId(Long accountId) throws GeneralBankingException {
		PreparedStatement preparedStatement = null;
		String message=null;
		Connection connection=null;
		int result=0;
		try {
			connection=H2DatabaseFactory.getConnection();
			preparedStatement = connection.prepareStatement(DELETE_ACC_ACCID);
			preparedStatement.setLong(1, accountId);
			result=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			message = "Error occured while deleting account";
			log.error(message, e);
			throw new GeneralBankingException(message);
		}finally {
			DbUtils.closeQuietly(preparedStatement);
			DbUtils.closeQuietly(connection);
		}
		return result;
	}

	@Override
	public synchronized int updateBankAccountsAsPerAccountId(BankAccount frmBankAccount,BankAccount toBankAccount) throws GeneralBankingException {
		PreparedStatement preparedStatement = null;
		String message=null;
		Connection connection=null;
		int result=0;
		try {
			connection=H2DatabaseFactory.getConnection();
			connection.setAutoCommit(false);
			if (frmBankAccount!=null) {
                preparedStatement = connection.prepareStatement(UPDATE_ACC);
                preparedStatement.setBigDecimal(1, frmBankAccount.getBalance());
                preparedStatement.setLong(2, frmBankAccount.getBankAccId());
                result=preparedStatement.executeUpdate();
            }

            if (toBankAccount!=null) {
                preparedStatement = connection.prepareStatement(UPDATE_ACC);
                preparedStatement.setBigDecimal(1, toBankAccount.getBalance());
                preparedStatement.setLong(2, toBankAccount.getBankAccId());
                preparedStatement.executeUpdate();
            }

            connection.commit();

		} catch (SQLException e) {
		    if(connection!=null){
                try {
                    log.error("Alert ! transaction is rolled back");
                    connection.rollback();
                } catch (SQLException ex) {
                    log.error("Exception occurred while rolling back while updating " +
                            "from account Id:"+frmBankAccount.getBankAccId()+" to account Id:"+toBankAccount.getBankAccId());
                }
            }
			message = "Error occured while deleting account";
			log.error(message, e);
			throw new GeneralBankingException(message);
		}finally {
			DbUtils.closeQuietly(preparedStatement);
			DbUtils.closeQuietly(connection);
		}
		return result;
	}

	@Override
	public synchronized List<BankAccount> getAccounts(long bankAccId) throws GeneralBankingException {
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		List<BankAccount> accounts = new ArrayList<BankAccount>();
		String message = null;
		Connection connection=null;

		try {
			connection=H2DatabaseFactory.getConnection();
			preparedStatement = connection.prepareStatement(GET_ACC_ACCID);
			preparedStatement.setLong(1, bankAccId);
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
			DbUtils.closeQuietly(preparedStatement);
			DbUtils.closeQuietly(connection);
		}

		return accounts;
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

}
