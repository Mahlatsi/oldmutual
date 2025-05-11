package com.flagexplorer.controller

import com.flagexplorer.model.Country
import com.flagexplorer.model.CountryDetails
import com.flagexplorer.service.CountryService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CountryController::class)
class CountryControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var countryService: CountryService

    @Test
    fun `getAllCountries should return list of countries`() {
        val countries = listOf(
            Country("Test Country", "test-flag.png")
        )
        `when`(countryService.getAllCountries()).thenReturn(countries)

        mockMvc.perform(get("/countries"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value("Test Country"))
            .andExpect(jsonPath("$[0].flag").value("test-flag.png"))
    }

    @Test
    fun `getCountryDetails should return country details when found`() {
        val countryDetails = CountryDetails(
            name = "Test Country",
            population = 1000000,
            capital = "Test Capital",
            flag = "test-flag.png"
        )
        `when`(countryService.getCountryDetails("Test Country")).thenReturn(countryDetails)

        mockMvc.perform(get("/countries/Test Country"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Test Country"))
            .andExpect(jsonPath("$.population").value(1000000))
            .andExpect(jsonPath("$.capital").value("Test Capital"))
            .andExpect(jsonPath("$.flag").value("test-flag.png"))
    }

    @Test
    fun `getCountryDetails should return 404 when country not found`() {
        `when`(countryService.getCountryDetails("NonExistent")).thenReturn(null)

        mockMvc.perform(get("/countries/NonExistent"))
            .andExpect(status().isNotFound)
    }
} 