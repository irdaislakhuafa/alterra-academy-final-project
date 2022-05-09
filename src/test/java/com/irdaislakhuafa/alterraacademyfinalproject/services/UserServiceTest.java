package com.irdaislakhuafa.alterraacademyfinalproject.services;

import java.util.HashSet;

import com.irdaislakhuafa.alterraacademyfinalproject.SimpleTestNameGenerator;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.Role;
import com.irdaislakhuafa.alterraacademyfinalproject.model.entities.User;
import com.irdaislakhuafa.alterraacademyfinalproject.model.repositories.UserRepository;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@Tag(value = "userServiceTest")
@DisplayNameGeneration(value = SimpleTestNameGenerator.class)
public class UserServiceTest implements BaseServiceTest {
    @MockBean
    private UserRepository userRepository;

    private final Role role = Role.builder()
            .id("roleId")
            .name("ROLE_ADMIN")
            .description("-")
            .build();

    private final User user = User.builder()
            .id("userId")
            .name("irda islakhu afa")
            .email("irdhaislakhuafa@gmail.com")
            .password("anaardani")
            .isEnable(true)
            .roles(new HashSet<>() {
                {
                    add(role);
                }
            })
            .build();

    @Override
    public void testSaveSuccess() {

    }

    @Override
    public void testSaveFailed() {

    }

    @Override
    public void testFindByIdSuccess() {

    }

    @Override
    public void testFindByIdFailed() {

    }

    @Override
    public void testDeleteByIdSuccess() {

    }

    @Override
    public void testDeleteByIdFailed() {

    }

    @Override
    public void testFindAllSuccess() {

    }

    @Override
    public void testMapToEntitySuccess() {

    }

    @Override
    public void testMapToEntityFailed() {

    }

    @Override
    public void testMapToEntitiesSuccess() {

    }

    @Override
    public void testMapToEntitiesFailed() {

    }

    @Override
    public void testSaveAllSuccess() {

    }

    @Override
    public void testSaveAllFailed() {

    }

    @Override
    public void testFindAllByIdSuccess() {

    }

    @Override
    public void testUpdateSuccess() {

    }

    @Override
    public void testUpdateFailed() {

    }

}
