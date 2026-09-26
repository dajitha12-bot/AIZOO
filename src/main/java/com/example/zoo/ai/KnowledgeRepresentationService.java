package com.example.zoo.ai;

import com.example.zoo.entity.EnrichmentKnowledge;
import com.example.zoo.entity.SpeciesFood;
import com.example.zoo.entity.SpeciesKnowledge;
import com.example.zoo.repository.EnrichmentKnowledgeRepository;
import com.example.zoo.repository.SpeciesFoodRepository;
import com.example.zoo.repository.SpeciesKnowledgeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KnowledgeRepresentationService {

    private final SpeciesKnowledgeRepository speciesKnowledgeRepository;
    private final SpeciesFoodRepository speciesFoodRepository;
    private final EnrichmentKnowledgeRepository enrichmentKnowledgeRepository;

    public KnowledgeRepresentationService(SpeciesKnowledgeRepository speciesKnowledgeRepository,
                                         SpeciesFoodRepository speciesFoodRepository,
                                         EnrichmentKnowledgeRepository enrichmentKnowledgeRepository) {
        this.speciesKnowledgeRepository = speciesKnowledgeRepository;
        this.speciesFoodRepository = speciesFoodRepository;
        this.enrichmentKnowledgeRepository = enrichmentKnowledgeRepository;
    }

    public Optional<SpeciesKnowledge> getSpeciesKnowledge(String speciesName) {
        return speciesKnowledgeRepository.findBySpeciesName(speciesName);
    }

    public List<SpeciesFood> getSuitableFoodsForSpecies(String speciesName) {
        return speciesFoodRepository.findBySpeciesName(speciesName);
    }

    public List<EnrichmentKnowledge> getEnrichmentsForSpecies(String speciesName) {
        return enrichmentKnowledgeRepository.findBySpeciesName(speciesName);
    }

    public List<SpeciesKnowledge> getAllSpeciesKnowledge() {
        return speciesKnowledgeRepository.findAll();
    }
}
