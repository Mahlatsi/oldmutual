package com.flagexplorer.controller

import com.flagexplorer.model.Country
import com.flagexplorer.model.CountryDetails
import com.flagexplorer.service.CountryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/all")
class CountryController(private val countryService: CountryService) {
    
    @GetMapping
    fun getAllCountries(): ResponseEntity<List<Country>> {
        return ResponseEntity.ok(countryService.getAllCountries())
    }
    
    @GetMapping("/{name}")
    fun getCountryDetails(@PathVariable name: String): ResponseEntity<CountryDetails> {
        return countryService.getCountryDetails(name)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }
} 