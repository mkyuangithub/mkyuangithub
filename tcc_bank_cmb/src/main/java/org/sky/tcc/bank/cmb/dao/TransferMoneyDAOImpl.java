package org.sky.tcc.bank.cmb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.sky.tcc.bean.AccountBean;
import org.sky.tcc.dao.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TransferMoneyDAOImpl extends BaseDAO implements TransferMoneyDAO {
	@Autowired
	private JdbcTemplate toJdbcTemplate;

	@Override
	public void addAccount(AccountBean account) throws Exception {
		String sql = "insert into bank_account(account_id,amount,freezed_amount) values(?,?,?)";
		toJdbcTemplate.update(sql, account.getAccountId(), account.getAmount(), account.getFreezedAmount());
	}

	@Override
	public int updateAmount(AccountBean account) throws Exception {
		String sql = "update bank_account set amount=?, freezed_amount=? where account_id=?";
		int result = 0;
		result = toJdbcTemplate.update(sql, account.getAmount(), account.getFreezedAmount(), account.getAccountId());
		return result;
	}

	@Override
	public AccountBean getAccount(String accountNo) throws Exception {
		String sql = "select account_id,amount,freezed_amount from bank_account where account_id=?";
		AccountBean account = null;
		// Object[] params = new Object[] { accountNo };
		try {
			account = toJdbcTemplate.queryForObject(sql, new RowMapper<AccountBean>() {
				@Override
				public AccountBean mapRow(ResultSet rs, int rowNum) throws SQLException {

					AccountBean account = new AccountBean();
					account.setAccountId(rs.getString("account_id"));
					account.setAmount(rs.getDouble("amount"));
					account.setFreezedAmount(rs.getDouble("freezed_amount"));
					return account;
				}
			}, accountNo);
		} catch (Exception e) {
			logger.error("getAccount error: " + e.getMessage(), e);
			account = null;
		}
		return account;
	}

	@Override
	public AccountBean getAccountForUpdate(String accountNo) throws Exception {
		String sql = "select account_id,amount,freezed_amount from bank_account where account_id=? for update";
		AccountBean account = null;
		// Object[] params = new Object[] { accountNo };
		try {
			account = toJdbcTemplate.queryForObject(sql, new RowMapper<AccountBean>() {
				@Override
				public AccountBean mapRow(ResultSet rs, int rowNum) throws SQLException {
					AccountBean account = new AccountBean();
					account.setAccountId(rs.getString("account_id"));
					account.setAmount(rs.getDouble("amount"));
					account.setFreezedAmount(rs.getDouble("freezed_amount"));
					return account;

				}
			}, accountNo);
		} catch (Exception e) {
			logger.error("getAccount error: " + e.getMessage(), e);
			account = null;
		}
		return account;
	}

	@Override
	public int updateFreezedAmount(AccountBean account) throws Exception {
		String sql = "update bank_account set freezed_amount=? where account_id=?";
		int result = 0;
		result = toJdbcTemplate.update(sql, account.getFreezedAmount(), account.getAccountId());
		return result;
	}

}
