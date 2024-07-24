package br.com.totvs.bills_manager.adapter.out.persistence.entity;

import br.com.totvs.bills_manager.domain.model.Role;
import br.com.totvs.bills_manager.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    public User toDomain(){
        return User.builder()
            .id(this.id)
            .login(this.login)
            .password(this.password)
            .role(Role.fromText(this.role))
        .build();
    }

    public static UserEntity fromDomain(User domain){
        return UserEntity.builder()
            .id(domain.getId())
            .login(domain.getLogin())
            .password(domain.getPassword())
            .role(domain.getRole().getText())
        .build();
    }
    
}
