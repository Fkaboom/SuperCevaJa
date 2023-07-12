package br.org.fundatec.SuperCevaJa.repository;

import br.org.fundatec.SuperCevaJa.model.BeerTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerTypeRepository extends JpaRepository<BeerTypeModel,Long> {
    BeerTypeModel findByName(String name);
}
