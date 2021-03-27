package travelbeeee.communityPjt.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import travelbeeee.communityPjt.domain.Writing;
import travelbeeee.communityPjt.repository.WritingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IWritingService implements WritingService {

    private final WritingRepository writingRepository;

    @Override
    public List<Writing> getWritings() {
        return writingRepository.selectAll();
    }

    @Override
    public Writing getWritingById(Long writingId) {
        Writing writing = writingRepository.selectById(writingId);
        return writing;
    }

    @Override
    public void insertWriting(Writing writing) {
        writingRepository.insert(writing);
        return;
    }

    @Override
    public void deleteWriting(Long writingId) {
        writingRepository.delete(writingId);
        return;
    }

    @Override
    public void updateViews(Long writingId) {
        writingRepository.updateViews(writingId);
        return;
    }

    @Override
    public void update(Writing writing) {
        writingRepository.update(writing);
        return;
    }
}
