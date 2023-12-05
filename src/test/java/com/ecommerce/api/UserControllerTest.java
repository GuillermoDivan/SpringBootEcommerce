package com.ecommerce.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import ecommerce.api.controllers.UserController;
import ecommerce.api.entities.User.*;
import ecommerce.api.services.User.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
/*
    @Test
    public void shouldCreateUser() throws Exception {
        //Crea el dto que se devuelve.
        UserShowData userShowData = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("Test");

        //Transforma el objeto del DTO a JSON para enviar.
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userDTO);

        //Mock del service...
        when(userService.saveUser(any(UserDTO.class)))
                .thenReturn(userDTO);

        //Emula la petición post a la uri del controller que acepta peticiones json
    mockMvc.perform(MockMvcRequestBuilders.post("/u/")
                    .contentType(MediaType.APPLICATION_JSON)
            .content(json))  //Como body se le pasa el json creado antes.
            //En lugar de corroborar con assert, se corrobora con andExpect.
            //Se espera un 200 (ok... se podría un 201 pero implica pasar uri en controller).
            .andExpect(MockMvcResultMatchers.status().isOk())
            //Se espera como respuesta el contenido json que se mockeó.
        .andExpect(content().json(json));
    }

    @Test
    public void shouldGetAnExistentUser() throws Exception {
        //Crea el dto que se devuelve.
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("Test");

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(userDTO);

        when(userService.findUserById(userDTO.getId()))
                .thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/u/{id}", userDTO.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(json));
    }


        @Test
        public void shouldGetNotFoundForANonExistentUser() throws Exception {

            when(userService.findUserById(anyLong()))
                    .thenThrow(EntityNotFoundException.class);

            mockMvc.perform(MockMvcRequestBuilders.get("/u/{id}", 123123))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }*/
}