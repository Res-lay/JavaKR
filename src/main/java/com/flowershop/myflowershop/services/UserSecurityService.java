package com.flowershop.myflowershop.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flowershop.myflowershop.models.Cart;
import com.flowershop.myflowershop.models.User;
import com.flowershop.myflowershop.repos.CartRepo;
import com.flowershop.myflowershop.repos.UserRepo;
import com.flowershop.myflowershop.security.UserSecurity;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class UserSecurityService implements UserDetailsService{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartRepo cartRepo;


    @Autowired
    private UserRepo userRepositroy;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserSecurity userSecurity = new UserSecurity(userRepositroy.findByUsername(username));
        if(userSecurity.getUser() == null){
            throw new UsernameNotFoundException("user with such username doesn't exist");
        }

        return userSecurity;
        
    }

    public List<User> getById(Long id){
        Optional<User> user = userRepositroy.findById(id);
        return user.map(Collections::singletonList).orElse(Collections.emptyList());
    }

    public User getByUsername(String username){
        return userRepositroy.findByUsername(username);
    }

    public void signUpUser(User user){
        boolean exist = userRepositroy.findByUsername(user.getUsername()) != null;
        if (exist) {
            throw new IllegalAccessError("User already exists");
        }
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userRepositroy.save(user);
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepo.save(cart);   
    }
}
