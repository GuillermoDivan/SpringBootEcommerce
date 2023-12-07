package ecommerce.api;

import ecommerce.api.entities.User.*;
import ecommerce.api.repositories.UserRepository;
import ecommerce.api.services.User.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

	@Autowired
	private UserService userService;
	@MockBean
	private UserRepository userRepository;

	@Test
	public void shouldCreateUser() {
	//Arrange (Organizar/Inicializa) => inicializa los objetos y establece los valores de
	// los datos que vamos a utilizar en el Test que lo contiene.
	//Crear el objeto a devolver la db, mockear el repositorio para que lo devuelva,
	//Y Crear el dto a usar.

	// Creación del input (parámetros).
	UserSaveData userSaveData = new UserSaveData("Test",
			"123", Role.ADMIN);

	//Creación del output (return).
	User user = new User();
	user.setId(1L);
	user.setUsername("Test");

	//Mocking.
	when(userRepository.save(any(User.class)))
			.thenReturn(user);

	//Act (Actuar) => realiza la llamada al método a probar con los parámetros preparados
	//para tal fin.
	UserShowData createdUser = userService.saveUser(userSaveData);

	//Assert (Confirmar/Comprobar) => comprueba que el método de pruebas ejecutado
	//se comporta tal y como teníamos previsto que lo hiciera.
	assertNotNull(createdUser.id());
	assertEquals("Test", createdUser.username());
}

	@Test
	public void shouldFindById() {

	User user = new User();
	user.setId(1L);
	user.setUsername("Test");

	when(userRepository.findByIdAndActive(anyLong(), anyBoolean())).thenReturn(Optional.of(user));

	UserShowData userFound= userService.findByIdAndActive(user.getId(), true);

	assertEquals(userFound.id(), 1L);
	assertEquals("Test", userFound.username());
	}

	@Test
	public void shouldNOTFindById() {
		when(userRepository.findByIdAndActive(anyLong(), anyBoolean())).thenReturn(Optional.empty());

		Throwable exception = assertThrows(EntityNotFoundException.class, () ->
				userService.findByIdAndActive(1L, true));

		assertEquals(null, exception.getMessage());
	}

	@Test
	public void shouldFindByUsername() {
		User user = new User();
		user.setId(1L);
		user.setUsername("Test");

		when(userRepository.findByUsernameAndActive(anyString(), anyBoolean())).thenReturn(Optional.of(user));

		UserShowData userFound= userService.findByUsernameAndActive(user.getUsername(), true);

		assertEquals(userFound.id(), 1L);
		assertEquals("Test", userFound.username());
	}

	@Test
	public void shouldNOTFindByUsername() {
		when(userRepository.findByUsernameAndActive(anyString(), anyBoolean())).thenReturn(Optional.empty());

		Throwable exception = assertThrows(EntityNotFoundException.class, () ->
				userService.findByUsernameAndActive("lalala", true));

		assertEquals(null, exception.getMessage());
	}

	@Test
	public void shouldFindAll() {

		List<User> userList = new ArrayList<>();
		User user = new User();
		user.setId(1L);
		user.setUsername("Test");
		userList.add(user);

		when(userRepository.findAllByActive(anyBoolean(), any(Pageable.class))).thenReturn(new PageImpl<>(userList));

		Page<UserShowData> userListFound= userService.findAllByActive(true, Pageable.ofSize(5));

		assertEquals(userListFound.getTotalElements(), 1);
		assertEquals(userListFound.get().findFirst().get().username(),  "Test");
	}

	@Test
	public void shouldNOTFindAll() {

		List<User> userList = new ArrayList<>();

		when(userRepository.findAllByActive(anyBoolean(), any(Pageable.class))).thenReturn(new PageImpl<>(userList));

		Page<UserShowData> userListFound= userService.findAllByActive(true, Pageable.ofSize(5));

		assertEquals(userListFound.getTotalElements(), 0);
	}

	@Test
	public void shouldUpdateUser(){
		UserUpdateData userUpdateData = new UserUpdateData(1L, "123");

		User user = new User();
		user.setId(1L);
		user.setUsername("Test");

		when(userRepository.findByIdAndActive(anyLong(), anyBoolean())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);

		UserShowData updatedUser = userService.updateUser(userUpdateData, true);

		assertEquals(updatedUser.id(), 1L);
		assertEquals("Test", updatedUser.username());
	}

	@Test
	public void shouldNOTFINDUpdateUser(){
		UserUpdateData userUpdateData = new UserUpdateData(1L, "123");

		when(userRepository.findByIdAndActive(anyLong(), anyBoolean())).thenReturn(Optional.empty());

		Throwable exception = assertThrows(EntityNotFoundException.class, () ->
				userService.updateUser(userUpdateData, true));

		assertEquals(null, exception.getMessage());
	}

	@Test
	public void shouldBeIllegalArgumentToUpdateUser(){
		UserUpdateData userUpdateData = new UserUpdateData(1L, null);

		User user = new User();
		user.setId(1L);
		user.setUsername("Test");

		when(userRepository.findByIdAndActive(anyLong(), anyBoolean())).thenReturn(Optional.of(user));

		Throwable exception = assertThrows(IllegalArgumentException.class, () ->
				userService.updateUser(userUpdateData, true));

		assertEquals(null, exception.getMessage());
	}

	@Test
	public void shouldHideUser(){
		Long id = 1L;

		User user = new User();
		user.setId(1L);
		user.setActive(true);

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);

		Boolean result = userService.turnOffUser(id);

		assertEquals(result, true);
	}

	@Test
	public void shouldNOTFINDHideUser(){
		Long id = 1L;

		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		Throwable exception = assertThrows(EntityNotFoundException.class, () ->
				userService.turnOffUser(id));

		assertEquals(null, exception.getMessage());
	}

	@Test
	public void shouldNOTHideUser(){
		Long id = 1L;

		User user = new User();
		user.setId(1L);
		user.setUsername("Test");
		user.setActive(false);

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

		Boolean result = userService.turnOffUser(id);

		assertEquals(result, false);
	}

	@Test
	public void shouldReactivateUser(){
		Long id = 1L;

		User user = new User();
		user.setId(1L);
		user.setActive(false);

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);

		Boolean result = userService.reactivateUser(id);

		assertEquals(result, true);
	}

	@Test
	public void shouldNOTFINDReactiveUser(){}

	@Test
	public void shouldNOTReactiveUser(){}

	/*//PENDIENTE TESTING CON INYECCIÓN DE AUTHENTICATION SERVICE PARA DELETE!
	@Test
	public void shouldDeleteUser(){
		Long id = 1L;

		User user = new User();
		user.setId(1L);

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

		Boolean result = userService.deleteUser(id);

		assertEquals(result, true);
	}*/



}

