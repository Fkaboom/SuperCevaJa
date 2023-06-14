package br.org.fundatec.SuperCevaJa.repository;

import br.org.fundatec.SuperCevaJa.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {

    UserModel findByLogin(String login);

}
