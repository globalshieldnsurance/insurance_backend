package com.hcl.einsurance.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class TopTrendingServiceImpl implements TopTrendingService {
	private static final Logger logger = LoggerFactory.getLogger(TopTrendingServiceImpl.class);
	@Autowired
	PurchaseRepository purchaseRepository;

	/**
	 * This method is intended to list top 10 trending policies based on the count
	 * 
	 * @exception TRENDINGS_NOT_FOUND if no trending found
	 * @return TrendingResponseDto which includes
	 *         policyId,policyName,count,percentage
	 */

	@Override
	public List<TrendResponseDto> getTopTrendingPolicies() {
		logger.info("top 10 trending policies in service");
		List<TrendResponseDto> responseList = new ArrayList<>();
		Pageable pageable = PageRequest.of(0, 10);
		List<TrendingResponseDto> trendingList = purchaseRepository.getTopTrendings(pageable);
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
