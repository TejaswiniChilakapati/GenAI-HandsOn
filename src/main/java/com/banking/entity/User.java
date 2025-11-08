package com.banking.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id ;//(Primary Key)
    
    @Column(nullable=false,unique=true)
	private String userName; //(Unique)
    
    @Column(nullable=false,unique=true)
	private String email; //(Unique)
    
    @Column(nullable=false)
	private String password; //(Encrypted)
    
    @Column(name="first_name")
	private String firstName;
    
    @Column(name="last_name")
	private String lastName;
    
   
	private String phone;
    
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
    private Role role;
    
	@Column(name="created_at")
	private LocalDateTime createdAt;

}
