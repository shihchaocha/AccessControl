package org.example.pap.services;
import org.example.pap.models.MatchingCriteria;
import org.example.pap.models.MatchingCriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchingCriteriaService {

    //應該要能夠找相關的條件出來，目前 find 的方式有點不行

    @Autowired
    private MatchingCriteriaRepository matchingCriteriaRepository;

    public List<MatchingCriteria> findAll() {
        return matchingCriteriaRepository.findAll();
    }

    public Optional<MatchingCriteria> findById(Long id) {
        return matchingCriteriaRepository.findById(id);
    }

    public MatchingCriteria save(MatchingCriteria matchingCriteria) {
        return matchingCriteriaRepository.save(matchingCriteria);
    }

    public void deleteById(Long id) {
        matchingCriteriaRepository.deleteById(id);
    }
}

