package org.sky.tcc.icbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.sky.tcc.bean.AccountBean;
import org.sky.tcc.dao.BaseDAO;
import org.sky.tcc.dao.TransferMoneyDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class TransferMoneyDAOImpl extends BaseDAO implements TransferMoneyDAO {
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addAccount(AccountBean account) throws Exception {
		String sql = "insert into bank_account(account_id,amount,freezed_amount) values(?,?,?)";
		jdbcTemplate.update(sql, account.getAccountId(), account.getAmount(), account.getFreezedAmount());
	}

	@Override
	public int updateAmount(AccountBean account) throws Exception {
		String sql = "update bank_account set amount=?, freezed_amount=? where account_id=?";
		int result = 0;
		result = jdbcTemplate.update(sql, account.getAmount(), account.getFreezedAmount(), account.getAccountId());
		return result;
	}

	@Override
	public AccountBean getAccount(long accountNo) throws Exception {
		String sql = "select account_id,amount,freezed_amount from bank_account where account_id=?";
		AccountBean account = null;
		// Object[] params = new Object[] { accountNo };
		account = jdbcTemplate.queryForObject(sql, new RowMapper<AccountBean>() {
			@Override
			public AccountBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				AccountBean account = new AccountBean();
				account.setAccountId(rs.getLong("account_id"));
				account.setAmount(rs.getDouble("amount"));
				account.setFreezedAmount(rs.getDouble("freezed_amount"));
				return account;
			}
		}, accountNo);
		return account;
	}

	@Override
	public AccountBean getAccountForUpdate(long accountNo) throws Exception {
		String sql = "select account_id,amount,freezed_amount from bank_account where account_id=? for update";
		AccountBean account = null;
		// Object[] params = new Object[] { accountNo };
		account = jdbcTemplate.queryForObject(sql, new RowMapper<AccountBean>() {
			@Override
			public AccountBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				AccountBean account = new AccountBean();
				account.setAccountId(rs.getLong("account_id"));
				account.setAmount(rs.getDouble("amount"));
				account.setFreezedAmount(rs.getDouble("freezed_amount"));
				return account;
			}
		}, accountNo);
		return account;
	}

	@Override
	public int updateFreezedAmount(AccountBean account) throws Exception {
		String sql = "update bank_account set freezed_amount=? where account_id=?";
		int result = 0;
		result = jdbcTemplate.update(sql, account.getFreezedAmount(), account.getAccountId());
		return result;
	}

}
