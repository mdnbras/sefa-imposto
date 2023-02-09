package sefa.desafio.imposto.domain.parcelamentodebito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelamentoDebitoRepository extends JpaRepository<ParcelamentoDebito, Long>, JpaSpecificationExecutor<ParcelamentoDebito> {
}
