package cloud.ieum.interest.service;

import cloud.ieum.interest.Interest;
import cloud.ieum.interest.repository.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestService {
    private final InterestRepository interestRepository;

    public void saveInterest(Long interestId, Long userId){
        interestRepository.save(Interest.builder().categoryId(interestId).userId(userId).build());
    }

    public void removeInterest(Long interestId, Long userId){
        Interest interest = interestRepository.findByUserIdAndCategoryId(userId, interestId).orElseThrow(() -> new IllegalArgumentException("not found interest"));
        interestRepository.delete(interest);

    }

    public List<Interest> findUserInterest(Long userId){
        return interestRepository.findAllByUserId(userId);
    }
}
