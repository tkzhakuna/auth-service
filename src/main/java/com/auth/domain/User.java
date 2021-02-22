package com.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class User implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "username is required")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Please enter your full name")
    private String fullname;
    //@JsonIgnore
    @NotBlank(message = "Password field is required")
    private String password;
    //@JsonIgnore
    @Transient
    private String confirmPassword;
    @CreatedDate
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    @JsonIgnore
    @Column(name="status")
	private Integer status;

    @Column(name="employee_id")
    private Integer employeeId;

    @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
   
    @Transient
   private Set<String> strRoles=new HashSet<>();

    @PrePersist
    protected void onCreate(){
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updateAt = LocalDateTime.now();
    }

    /*
    UserDetails interface methods
     */
    @Transient
    private Collection<? extends GrantedAuthority> authorities;
    public User(Integer id, String username, String fullname, String password,Integer employeeId,
			Collection<GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.password = password;
		this.employeeId=employeeId;
		this.authorities = authorities;
	}

    
    public  User build(User user) {
    	List<GrantedAuthority> authorities = getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());

		return new User(
				getId(), 
				getUsername(), 
				getFullname(),
				getPassword(), 
				getEmployeeId(),
				authorities);
	}
    
    
    
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
    
   }
