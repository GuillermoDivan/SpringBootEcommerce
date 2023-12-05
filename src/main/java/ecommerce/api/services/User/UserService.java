package ecommerce.api.services.User;
import ecommerce.api.entities.User.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserShowData saveUser(UserSaveData userSaveData);
    UserShowData findByIdAndActive(Long id, boolean active) throws EntityNotFoundException;
    UserShowData findByUsernameAndActive(String username, boolean active) throws EntityNotFoundException;
    Page<UserShowData> findAllByActive(boolean active, Pageable paging);
    UserShowData updateUser(UserUpdateData userUpdateData, boolean active) throws EntityNotFoundException;
    Boolean turnOffUser(Long id) throws EntityNotFoundException;
    Boolean reactivateUser(Long id) throws EntityNotFoundException;
    Boolean deleteUser(Long id) throws EntityNotFoundException;




}
