package com.disneyAPI.service;

import com.disneyAPI.domain.User;
import com.disneyAPI.dtos.JwtDTO;
import com.disneyAPI.dtos.UserDTO;
import com.disneyAPI.exceptions.DuplicateEmailException;
import com.disneyAPI.exceptions.UserNotFoundException;
import com.disneyAPI.mapper.RoleMapper;
import com.disneyAPI.mapper.UserMapper;
import com.disneyAPI.repository.RoleRepository;
import com.disneyAPI.repository.UserRepository;
import com.disneyAPI.repository.model.RoleModel;
import com.disneyAPI.repository.model.UserModel;
import com.disneyAPI.security.JwtProvider;
import com.disneyAPI.security.MainUser;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import static com.disneyAPI.mapper.UserMapper.mapModelToDomain;

public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,JwtProvider jwtProvider,
                       AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public UserDTO registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException("This email is in use");
        }
        RoleModel roleModel = roleRepository.findByName("USER");
        user.setRole(RoleMapper.mapModelToDomain(roleModel));
        UserModel userModel = UserMapper.mapDomainToModel(user);
        userModel.setPassword(encryptPassword(user));
        UserModel save = userRepository.save(userModel);
        User userDomain = mapModelToDomain(save);
        return UserMapper.mapDomainToDTO(userDomain);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAll() {
        List<UserModel> userModelList = userRepository.findAll();
        return userModelList.stream().map(UserMapper::mapModelToDomain)
                .map(UserMapper::mapDomainToDTO)
                .collect(Collectors.toList());
    }

    private String encryptPassword(User user) {
        String password = user.getPassword();
        String encryptedPassword = passwordEncoder.encode(password);
        return encryptedPassword;
    }

    @Transactional
    public UserDTO getAuthenticatedUser(String jwt) throws UserNotFoundException {
        String email = jwtProvider.getEmailFromToken(jwt);
        UserModel userModel = userRepository.findByEmail(email);
        User user = UserMapper.mapModelToDomain(userModel);
        return UserMapper.mapDomainToDTO(user);
    }

    public JwtDTO generateAuthenticationToken(User userDomain) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDomain.getEmail(), userDomain.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        MainUser userLog = (MainUser) authentication.getPrincipal();
        return new JwtDTO(jwt, userLog.getEmail());
    }
}
