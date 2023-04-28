package com.epam.recommendationservice.controller;

import com.epam.recommendationservice.model.CryptoNormalized;
import com.epam.recommendationservice.model.CryptoStats;
import com.epam.recommendationservice.service.RecommenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommenderController {
    private final RecommenderService recommenderService;

    /**
     * Fetches a {@link CryptoStats} for a cryptocurrency specified in a param
     * .
     *
     * @param symbol - crypto symbol which statistics should be returned
     * @return statistics of a crypto created from a corresponding data file
     */
    @GetMapping("/{symbol}")
    public CryptoStats getCryptoSummary(@PathVariable(name = "symbol") String symbol) {
        return recommenderService.getCryptoSummary(symbol);
    }

    /**
     * Fetches a descending sorted set of a {@link CryptoNormalized} cryptos, supported by the system.
     *
     * @return set of a cryptos with normalized prices calculated
     */
    @GetMapping("/comparision")
    public Set<CryptoNormalized> getCryptoComparision() {
        return recommenderService.getNormalizedCryptosSorted();
    }
}
