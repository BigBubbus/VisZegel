/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.model;

import org.eclipse.jetty.server.Authentication.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author tnowicki
 */
@Service
public class TEST implements UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
    UserDetails userDetails = null;
    User user = userService.getByLogin(login);
    userDetails = new CustomUserDetails(user,
                true, true, true, true,
                getAuthorities(user.getRole()));

    return userDetails;
    }

}