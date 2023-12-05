package ecommerce.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ecommerce.api.entities.User.*;
import ecommerce.api.services.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldCreateUser() throws Exception {
        //Transforma el objeto del DTO a JSON para enviar.
        UserSaveData userSaveData = new UserSaveData("test@test.com", "123", Role.ADMIN);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userSaveData);

        //Crea el dto que se devuelve (como JSON, en vista de insomnia).
        UserShowData userShowData = new UserShowData(1L, "test@test.com");
        String json2 = ow.writeValueAsString(userShowData);

        //Mock del service...
        when(userService.saveUser(any(UserSaveData.class)))
                .thenReturn(userShowData);

        //Emula la petición post a la uri del controller que acepta peticiones json
    mockMvc.perform(MockMvcRequestBuilders.post("/user/save")
                    .contentType(MediaType.APPLICATION_JSON)
            .content(json))  //Como body se le pasa el json creado antes.
            //En lugar de corroborar con assert, se corrobora con andExpect.
            //Se espera un 200 (ok... se podría un 201 pero implica pasar uri en controller).
            .andExpect(MockMvcResultMatchers.status().isOk())
            //Se espera como respuesta el contenido json que se mockeó.
        .andExpect(content().json(json2));
    }


    @Test
    public void shouldGetAnExistentUser() throws Exception {
        //Input = id.
        Long id = 1L;

        //Crea el dto que se devuelve (como JSON, en vista de insomnia).
        UserShowData userShowData = new UserShowData(1L, "test@test.com");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userShowData);

        when(userService.findByIdAndActive(id, true))
                .thenReturn(userShowData);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/id/{id}", userShowData.id())
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJFY29tbWVyY2UiLCJzdWIiOiJjb3NtZWZ1bGFuaXRvQHVzZXIxLmNvbSIsImlkIjoxLCJleHAiOjE3MDE5NjQ3Mzl9.W2nCUwRty4ZgyINPnQmHP_DkPrc2RRtcfIdJnHjjzH8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(json));
    }


        @Test
        public void shouldGetNotFoundForANonExistentUser() throws Exception {

            when(userService.findByIdAndActive(anyLong(), anyBoolean()))
                    .thenReturn(null);

            mockMvc.perform(MockMvcRequestBuilders.get("/user/id/{id}", 123465)
                            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJFY29tbWVyY2UiLCJzdWIiOiJjb3NtZWZ1bGFuaXRvQHVzZXIxLmNvbSIsImlkIjoxLCJleHAiOjE3MDE5NjQ3Mzl9.W2nCUwRty4ZgyINPnQmHP_DkPrc2RRtcfIdJnHjjzH8"))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
}