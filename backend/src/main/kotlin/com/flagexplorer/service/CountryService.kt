package com.flagexplorer.service

import com.flagexplorer.model.Country
import com.flagexplorer.model.CountryDetails
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.client.RestClientException

@Service
class CountryService(private val restTemplate: RestTemplate) {
    
    private val logger = LoggerFactory.getLogger(CountryService::class.java)
    private val baseUrl = "https://restcountries.com/v3.1"
    
    fun getAllCountries(): List<Country> {
        return try {
            val response = restTemplate.getForObject<Array<Map<String, Any>>>("$baseUrl/all")
            response?.mapNotNull { country ->
                try {
                    val nameObj = country["name"] as? Map<String, Any>
                    val commonName = nameObj?.get("common") as? String
                    val flags = country["flags"] as? Map<String, String>
                    val flagUrl = flags?.get("png")
                    
                    if (commonName != null && flagUrl != null) {
                        Country(name = commonName, flag = flagUrl)
                    } else {
                        logger.warn("Skipping country with missing name or flag: $country")
                        null
                    }
                } catch (e: Exception) {
                    logger.error("Error processing country data: ${e.message}", e)
                    null
                }
            } ?: emptyList()
        } catch (e: RestClientException) {
            logger.error("Error fetching countries from API: ${e.message}", e)
            emptyList()
        }
    }
    
    fun getCountryDetails(name: String): CountryDetails? {
        return try {
            val response = restTemplate.getForObject<Array<Map<String, Any>>>("$baseUrl/name/$name")
            response?.firstOrNull()?.let { country ->
                try {
                    val nameObj = country["name"] as? Map<String, Any>
                    val commonName = nameObj?.get("common") as? String
                    val population = (country["population"] as? Number)?.toLong()
                    val capital = (country["capital"] as? List<String>)?.firstOrNull()
                    val flags = country["flags"] as? Map<String, String>
                    val flagUrl = flags?.get("png")
                    
                    if (commonName != null && population != null && flagUrl != null) {
                        CountryDetails(
                            name = commonName,
                            population = population,
                            capital = capital ?: "N/A",
                            flag = flagUrl
                        )
                    } else {
                        logger.warn("Missing required fields for country: $name")
                        null
                    }
                } catch (e: Exception) {
                    logger.error("Error processing country details: ${e.message}", e)
                    null
                }
            }
        } catch (e: RestClientException) {
            logger.error("Error fetching country details from API: ${e.message}", e)
            null
        }
    }
} 