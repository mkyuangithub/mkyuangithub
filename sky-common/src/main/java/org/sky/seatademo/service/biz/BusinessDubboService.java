package org.sky.seatademo.service.biz;

import org.sky.exception.DemoRpcRunTimeException;
import org.sky.seatademo.vo.SeataProductVO;

public interface BusinessDubboService {

	public SeataProductVO addProductAndStock(SeataProductVO vo) throws DemoRpcRunTimeException;
	public SeataProductVO addProductAndStockFailed(SeataProductVO vo) throws DemoRpcRunTimeException;
}
