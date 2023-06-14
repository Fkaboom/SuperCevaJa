package br.org.fundatec.SuperCevaJa.repository;

import br.org.fundatec.SuperCevaJa.model.BeerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends JpaRepository<BeerModel,Long> {
    void deleteByName(String name);
}
