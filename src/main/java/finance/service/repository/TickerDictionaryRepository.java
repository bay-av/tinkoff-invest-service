package finance.service.repository;

import finance.service.entity.TickerDictionaryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TickerDictionaryRepository extends CrudRepository<TickerDictionaryEntity, Long> {
     TickerDictionaryEntity findByTcsTicker(String ticker);
}
