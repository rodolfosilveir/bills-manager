package br.com.totvs.bills_manager.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.totvs.bills_manager.adapter.out.persistence.entity.UserEntity;
import br.com.totvs.bills_manager.adapter.out.persistence.repository.UserRepository;
import br.com.totvs.bills_manager.domain.model.User;
import br.com.totvs.bills_manager.domain.model.UserMock;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserAdapter userAdapter;

    @Test
    @DisplayName("Deve retonar User vazio, metodo findByLogin")
    void shouldReturnsEmptyUserFindByLogin(){

        String username = "user";
        when(userRepository.findByLogin(username)).thenReturn(Optional.empty());

        Optional<UserDetails> user = userAdapter.findByLogin(username);

        assertTrue(user.isEmpty());
    }

    @Test
    @DisplayName("Deve retonar User, metodo findByLogin")
    void shouldReturnsUserFindByLogin(){

        String username = "user";
        when(userRepository.findByLogin(username))
            .thenReturn(Optional.of(UserEntity.fromDomain(UserMock.create(username))));

        Optional<UserDetails> user = userAdapter.findByLogin(username);

        assertTrue(user.isPresent());
        assertEquals(username, user.get().getUsername());
    }

    @Test
    @DisplayName("Deve salvar User, metodo save")
    void shouldSaveUserSuccessfulySave(){

        String username = "user";

        User userDomain = UserMock.create(username);
        when(userRepository.save(any()))
            .thenReturn(UserEntity.fromDomain(userDomain));

        userAdapter.save(userDomain);

         verify(userRepository, times(1)).save(any());
    }
    
}
