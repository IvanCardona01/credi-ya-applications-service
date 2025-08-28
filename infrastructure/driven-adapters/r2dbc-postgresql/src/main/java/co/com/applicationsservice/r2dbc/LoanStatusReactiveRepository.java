package co.com.applicationsservice.r2dbc;

import co.com.applicationsservice.r2dbc.entity.LoanStatusEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoanStatusReactiveRepository extends ReactiveCrudRepository<LoanStatusEntity, Long>, ReactiveQueryByExampleExecutor<LoanStatusEntity>  {

}
