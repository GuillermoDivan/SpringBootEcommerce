package ecommerce.api.services.User;
import ecommerce.api.entities.User.*;
import ecommerce.api.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserShowData saveUser(UserSaveData userSaveData) {
        User user = new User(userSaveData);
        user.setPassword(this.passwordEncoder.encode(userSaveData.password()));
        //Importante codificar el password ANTES de guardar a DB.
        var user1 = this.userRepository.save(user);
        return new UserShowData(user1);
    }

    @Override
    public UserShowData findByIdAndActive(Long id, boolean active)
            throws EntityNotFoundException {
        User user = this.userRepository.findByIdAndActive(id, active)
                .orElseThrow(EntityNotFoundException::new);
        return new UserShowData(user);
    }

    @Override
    public UserShowData findByUsernameAndActive(String username, boolean active)
            throws EntityNotFoundException {
        User user = this.userRepository.findByUsernameAndActive(username, active)
                .orElseThrow(EntityNotFoundException::new);
        return new UserShowData(user);
    }

    @Override
    public Page<UserShowData> findAllByActive(boolean active, Pageable paging) {
        return this.userRepository.findAllByActive(active, paging)
                .map(UserShowData::new);
    }

    @Override
    public UserShowData updateUser(UserUpdateData userUpdateData, boolean active)
            throws EntityNotFoundException {
        User user = this.userRepository.findByIdAndActive(userUpdateData.id(), active)
                .orElseThrow(EntityNotFoundException::new);
            if (userUpdateData.password() != null) {
                user.setPassword(userUpdateData.password());
                this.userRepository.save(user);
                return new UserShowData(user);}
            else { throw new IllegalArgumentException(); }
    }

    @Override
    public Boolean turnOffUser(Long id) throws EntityNotFoundException {
        User user = this.userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (user.isActive()) {
            user.setActive(false);
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean reactivateUser(Long id) throws EntityNotFoundException {
        User user = this.userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (!user.isActive()) {
            user.setActive(true);
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteUser(Long id) throws EntityNotFoundException {
        User user = this.userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        this.userRepository.deleteById(id);
        return true;
    }

}
