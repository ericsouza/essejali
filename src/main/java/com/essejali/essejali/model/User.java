package com.essejali.essejali.model;

import static org.hibernate.annotations.CascadeType.DELETE_ORPHAN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private String email;
    private String password;
    private String nameUser;
    
    @Column(columnDefinition = "integer default 0")
    private int points;
    
    @Column(columnDefinition = "varchar(255) default ''")
    private String trophies = "";

    @ManyToMany(fetch = FetchType.EAGER)
	private List<Profile> profiles = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<>();

	public User(String email, String password, String nameUser, List<Profile> profiles) {
    	this.email = email;
    	this.password = password;
    	this.nameUser = nameUser;
    	this.profiles = profiles;
    	this.points = 0;
    	this.trophies = "";
    }
    
    protected User() {}
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

    public long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.email;
    }
    
    public String getName() {
    	return this.nameUser;
    }

    public String getPassword() {
        return this.password;
    }
    
    public int getPoints() {
    	return this.points;
    }
    
    public void setPoints(int points) {
    	this.points = points;
    }
    
    public String getTrophies() {
    	return this.trophies;
    }
    
    public void setTrophies(String trophies) {
    	this.trophies = trophies;
    }
    
    public List<String> getTrophiesList() {
    	if(this.trophies.length() > 0){
            return Arrays.asList(this.trophies.split(","));
        }
        return new ArrayList<>();
    }
    
    public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.profiles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
