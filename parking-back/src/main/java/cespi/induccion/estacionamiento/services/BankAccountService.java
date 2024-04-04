package cespi.induccion.estacionamiento.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cespi.induccion.estacionamiento.models.User;
import cespi.induccion.estacionamiento.models.BankAccount;
import cespi.induccion.estacionamiento.repositories.BankAccountRepository;

@Service
@Transactional
public class BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	public double substractBalance(User user, double amount) {
		BankAccount account = user.getBankAccount();
		double balance = account.getBalance();
		double newBalance = balance-amount;
		account.setBalance(newBalance);
		bankAccountRepository.save(account);
		return newBalance;
	}
	
	public BankAccount create() {
		BankAccount account = new BankAccount();
		this.bankAccountRepository.save(account);
		return account;
	}
}
