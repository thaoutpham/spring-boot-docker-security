package crm.wealth.management.repository;

import crm.wealth.management.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends PagingAndSortingRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    @Query(value = "FROM AppUser u where u.username != 'sysadmin' AND u.fullName LIKE :keyword ORDER BY u.id")
    Page<AppUser> findAllUsers(String keyword, Pageable pageable);

    @Query(value = "SELECT DISTINCT r.name FROM tbl_role_permission_activities rpa " +
            "INNER JOIN tbl_roles r ON rpa.role_id = r.id " +
            "INNER JOIN tbl_user_roles usr ON usr.role_id = r.id " +
            "WHERE usr.user_id = (SELECT u.id FROM tbl_users u WHERE u.username = :username)", nativeQuery = true)
    List<String> findRoleByUsername(String username);

    AppUser findByEmail(String email);
}