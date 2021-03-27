package travelbeeee.communityPjt.repository;

import travelbeeee.communityPjt.domain.Files;

public interface FileRepository {
    int insert(Files file);
    int update(Files file);
    int delete(Long fildId);
}
