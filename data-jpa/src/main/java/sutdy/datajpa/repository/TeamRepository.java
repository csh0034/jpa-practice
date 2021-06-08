package sutdy.datajpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sutdy.datajpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
