package com.hcl.einsurance.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.einsurance.dto.TrendResponseDto;
import com.hcl.einsurance.dto.TrendingResponseDto;
import com.hcl.einsurance.exception.CommonException;
import com.hcl.einsurance.repository.PurchaseRepository;
import com.hcl.einsurance.util.EinsuranceConstants;

/**
 * @author DeepikaSivarajan
 *
 */
@Service
public class TrendingServiceImpl implements TrendingService {
	private static final Logger logger = LoggerFactory.getLogger(TrendingServiceImpl.class);
	@Autowired
	PurchaseRepository purchaseRepository;

	/**
	 * This method is intended to list trending policies based on the count
	 * 
	 * @exception TRENDINGS_NOT_FOUND if no trending found
	 * @return TrendingResponseDto which includes
	 *         policyId,policyName,count,percentage
	 */

	@Override
	public List<TrendResponseDto> getAllTrendingPolicies() {
		logger.info("trending policies in service");
		List<TrendResponseDto> responseList = new ArrayList<>();
		List<TrendingResponseDto> trendingList = purchaseRepository.getAllTrendings();
		if (trendingList.isEmpty()) {
			throw new CommonException(EinsuranceConstants.TRENDINGS_NOT_FOUND);
		} else {
			trendingList.stream().forEach(t -> {
				TrendResponseDto response = new TrendResponseDto();
				Double percent = (t.getCount() / 10d) * 100d;
				response.setPercentage(percent);
				BeanUtils.copyProperties(t, response);
				responseList.add(response);
			});
		}
		return responseList;
	}


}
