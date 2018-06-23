
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.ExamClass;

@Repository
public interface ExamClassRepository extends JpaRepository<ExamClass, Integer> {

	@Query("select e from ExamClass e where e.newspaper.id=?1")
	Collection<ExamClass> findAllByNewspaperId(Integer newspaperId);

	@Query("select e from ExamClass e where e.admin.id=?1")
	Collection<ExamClass> findAllByOwnerId(Integer ownerId);

	@Query("select e from ExamClass e where e.newspaper.id=?1 and e.draftMode=false")
	Collection<ExamClass> findAllFinalByNewspaperId(int newspaperId);

	@Query(value="select a.name, count(*) "
			+ "from ExamClass e "
			+ "join Administrator a "
			+ "where e.admin_id=a.id "
			+ "group by a.id "
			+ "having count(*)=(select max(cuenta.val) from (select count(*) as val from ExamClass e join Administrator a where e.admin_id=a.id group by a.id) cuenta)", nativeQuery=true)
	Collection<Object> findTopExamClassAdmines();

	
}
