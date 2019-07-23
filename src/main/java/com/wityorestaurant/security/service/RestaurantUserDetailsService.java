package com.wityorestaurant.security.service;

import com.wityorestaurant.modules.user.model.RestaurantUser;
import com.wityorestaurant.modules.user.repository.RestaurantUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RestaurantUserDetailsService implements UserDetailsService  {

	@Autowired
	RestaurantUserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
			RestaurantUser user = userRepo.findByUsername(username);
			if(user == null){
				throw new UsernameNotFoundException("No user found with this username");
			}
			return new org.springframework.security.core.userdetails.User
					(user.getUsername(), user.getPassword(), getAuthority(user));
		}
		catch (Exception e) {
			throw new UsernameNotFoundException("No user found with this username");
		}
	}
	public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
		try {
			RestaurantUser user = userRepo.findByUserId(userId);
			if (user == null) {
				throw new UsernameNotFoundException("No user found with this username");
			}
			return new org.springframework.security.core.userdetails.User
					(user.getUsername(), user.getPassword(), getAuthority(user));
		}
		catch (Exception e) {
			throw new UsernameNotFoundException("No user found with this username");
		}
	}

	private Set<SimpleGrantedAuthority> getAuthority(RestaurantUser user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName().toString()));
		});
		return authorities;
	}

}
