package com.kanaiya.webserviceclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kanaiya.webserviceclient.webservice.CountryClient;

import hello.wsdl.Country;

@RestController
@RequestMapping("/rest/country")
public class CountryController {
	@Autowired
	CountryClient countryClient;

	@GetMapping("/{countryName}")
	public Country hello(@PathVariable(name="countryName",value="countryName") String countryName) {
		return countryClient.getCountry(countryName).getCountry();

	}

}
