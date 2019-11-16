package com.revolut.banking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.revolut.banking.exceptions.BalanceNotEnoughException;
import com.revolut.banking.exceptions.GeneralBankingException;
import com.revolut.banking.resources.BankingResource;
import org.apache.log4j.Logger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class BankAccount {

	static Logger log = Logger.getLogger(BankAccount.class.getName());
	
	@JsonIgnore
	private long bankAccId;

	@NotNull
	@JsonProperty(required=true)
	private String bankAccHolderName;

	@NotNull
	@JsonProperty(required=true)
	private BigDecimal balance;

	@NotNull
	@JsonProperty(required=true)
	private String currencyCode;

	@NotNull
	@JsonProperty(required=true)
	private String emailId;

	@NotNull
	@JsonProperty(required=true)
	private String SSID;

	@NotNull
	@JsonProperty(required=true)
	private String contact;

	@NotNull
	@Size(min=3,max=3)
	@JsonProperty(required=true)
	private String strAccountType;
	
	@JsonIgnore
	private String strStatus;
	
	@JsonIgnore
	private BankAccType accountType;
	
	@JsonIgnore
	private BankAccountStatus status;
	
	@JsonIgnore
	private String creationDate;
	
	@JsonIgnore
	private String modifiedDate;

	public BankAccount(long bankAccId, String bankAccHolderName, BigDecimal balance, String currencyCode,
			String emailId, String sSID, String contact) throws GeneralBankingException {
		super();
		if(currencyCode.length()!=3) throw new GeneralBankingException("The currency code is wrong");
		this.bankAccId = bankAccId;
		this.bankAccHolderName = bankAccHolderName;
		this.balance = balance;
		this.currencyCode = currencyCode;
		this.emailId = emailId;
		SSID = sSID;
		this.contact = contact;
	}

	public BankAccount(String bankAccHolderName, BigDecimal balance, String currencyCode, String emailId, String sSID,
			String contact) throws GeneralBankingException {
		super();
		if(currencyCode.length()!=3) throw new GeneralBankingException("The currency code is wrong");
		this.bankAccHolderName = bankAccHolderName;
		this.balance = balance;		
		this.currencyCode = currencyCode;
		this.emailId = emailId;
		SSID = sSID;
		this.contact = contact;
	}

	public BankAccount() {
		super();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((SSID == null) ? 0 : SSID.hashCode());
		result = prime * result + ((bankAccHolderName == null) ? 0 : bankAccHolderName.hashCode());
		result = prime * result + (int) (bankAccId ^ (bankAccId >>> 32));
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		if (SSID == null) {
			if (other.SSID != null)
				return false;
		} else if (!SSID.equals(other.SSID))
			return false;
		if (bankAccHolderName == null) {
			if (other.bankAccHolderName != null)
				return false;
		} else if (!bankAccHolderName.equals(other.bankAccHolderName))
			return false;
		if (bankAccId != other.bankAccId)
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		return true;
	}

	public BankAccountStatus getStatus() {
		return status;
	}

	public void setStatus(BankAccountStatus status) {
		this.status = status;
	}

	public long getBankAccId() {
		return bankAccId;
	}

	public String getBankAccHolderName() {
		return bankAccHolderName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getSSID() {
		return SSID;
	}

	public String getContact() {
		return contact;
	}

	public BankAccType getAccountType() {
		return accountType;
	}

	public void setAccountType(BankAccType accountType) {
		this.accountType = accountType;
	}

	public String getStrAccountType() {
		return strAccountType;
	}

	public void setStrAccountType(String strAccountType) {
		this.strAccountType = strAccountType;
		
		if(this.strAccountType.toUpperCase().contains("CURR")) {
			this.accountType = BankAccType.CURR;
		}else if(this.strAccountType.toUpperCase().contains("SAV")) {
			this.accountType = BankAccType.SAV;
		}
		this.setAccountType(this.accountType);
	}
	
	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
		
		if(this.strStatus.toUpperCase().contains("A")) {
			this.status=BankAccountStatus.ACTIVE;
		}else if(this.strStatus.toUpperCase().contains("D")) {
			this.status=BankAccountStatus.DORMANT;
		}
	}
	
	
	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public synchronized void withDraw(BigDecimal amount) throws GeneralBankingException, BalanceNotEnoughException {
			if (this.balance.compareTo(amount) == 1) {
				log.info("Amount to be withdrawn :" + amount + " from balance:" + this.balance);
				try {
					Thread.sleep(100);
					this.balance = this.balance.subtract(amount);
				} catch (Exception ex) {
					log.error("Exception occurred while withDraw",ex);
					throw new GeneralBankingException("Exception occurred while withdrawing");
				}
			}else{
				throw new BalanceNotEnoughException();
			}

	}

	public synchronized void deposit(BigDecimal amount) throws GeneralBankingException, BalanceNotEnoughException {
			if(amount.compareTo(BigDecimal.ZERO) > 0){
				log.info("Amount to be withdrawn :" + amount + " from balance:" + this.balance);
				try {
					Thread.sleep(100);
					this.balance = this.balance.add(amount);
				} catch (Exception ex) {
					log.error("Exception occurred while Deposit",ex);
					throw new GeneralBankingException("Exception occurred while depositing");
				}
			}else{
				throw new BalanceNotEnoughException("The amount to be deposited is less than 0");
			}
	}

	@Override
	public String toString() {
		return "BankAccount [bankAccId=" + bankAccId + ", bankAccHolderName=" + bankAccHolderName + ", balance="
				+ balance + ", currencyCode=" + currencyCode + ", emailId=" + emailId + ", SSID=" + SSID + ", contact="
				+ contact + ", strAccountType=" + strAccountType + ", strStatus=" + strStatus + ", accountType="
				+ accountType + ", status=" + status + "]";
	}
	
	
	
}
