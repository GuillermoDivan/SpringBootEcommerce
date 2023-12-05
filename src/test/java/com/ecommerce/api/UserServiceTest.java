package com.ecommerce.api;

import ecommerce.api.entities.User.*;
import ecommerce.api.repositories.UserRepository;
import ecommerce.api.services.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
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

/*
@Test
public void shouldFindById() {
	User user = new User();
	user.setId(1L);
	user.setUsername("Test");

when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

UserDTO usuarioEncontrado = userService.findUserById(user.getId());

assertEquals(usuarioEncontrado.getId(), 1L);
assertEquals("Test", usuarioEncontrado.getUsername());
}*/
}

