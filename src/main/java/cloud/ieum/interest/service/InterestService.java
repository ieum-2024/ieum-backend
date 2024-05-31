package cloud.ieum.interest.service;

import cloud.ieum.interest.Interest;
import cloud.ieum.interest.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InterestService {
    private final InterestRepository interestRepository;

    public void saveInterest(Integer interestId, Integer userId){
        interestRepository.save(Interest.builder().categoryId(interestId).userId(userId).build());
    }

    public void removeInterest(Integer interestId, Integer userId){
        Interest interest = interestRepository.findByUserIdAndCategoryId(userId, interestId).orElseThrow(() -> new IllegalArgumentException("not found interest"));
        interestRepository.delete(interest);
    }

    public Boolean isActiveInterest(Integer interestId, Integer userId){
        Optional<Interest> interest = interestRepository.findByUserIdAndCategoryId(userId, interestId);
        return interest.isPresent();
    }

    public List<Interest> findUserInterest(Integer userId){
        return interestRepository.findAllByUserId(userId);
    }
}
