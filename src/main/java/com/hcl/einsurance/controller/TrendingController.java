package com.hcl.einsurance.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.einsurance.dto.TrendResponseDto;
import com.hcl.einsurance.service.TopTrendingService;
import com.hcl.einsurance.service.TrendingService;

import io.swagger.annotations.ApiOperation;

/**
 * @author DeepikaSivarajan
 *
 */
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/api")
public class TrendingController {
	private static final Logger logger = LoggerFactory.getLogger(TrendingController.class);
	@Autowired
	TrendingService trendingService;
	@Autowired
	TopTrendingService topTrendingService;
	/**
	 * This method is intended to list trending policies based on the count
	 * 
	 * @return TrendingResponseDto which includes
	 *         policyId,policyName,count,percentage
	 */
	@ApiOperation(value = "It gives the trending policies who are purchased mostly")
	@GetMapping("/trending")
	public ResponseEntity<List<TrendResponseDto>> getAllTrendingPolicies() {
		logger.info("trending policies");
		List<TrendResponseDto> response = trendingService.getAllTrendingPolicies();
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@ApiOperation(value = "It gives the top 10 trending policies who are purchased mostly")
	@GetMapping("/topTrending")
	public ResponseEntity<List<TrendResponseDto>> getTopTrendingPolicies() {
		logger.info("top trending policies");
		List<TrendResponseDto> response = topTrendingService.getTopTrendingPolicies();
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
