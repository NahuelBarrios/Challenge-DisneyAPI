package com.disneyAPI.service;

import com.disneyAPI.domain.User;
import com.disneyAPI.dtos.JwtDTO;
import com.disneyAPI.dtos.UserDTO;
import com.disneyAPI.exceptions.DisneyRequestException;
import com.disneyAPI.mapper.RoleMapper;
import com.disneyAPI.mapper.UserMapper;
import com.disneyAPI.repository.RoleRepository;
import com.disneyAPI.repository.UserRepository;
import com.disneyAPI.repository.model.RoleModel;
import com.disneyAPI.repository.model.UserModel;
import com.disneyAPI.security.JwtProvider;
import com.disneyAPI.security.MainUser;
import com.sendgrid.helpers.mail.objects.Email;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
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
    private final EmailService emailService;

    public UserService(UserRepository userRepository,RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,JwtProvider jwtProvider,
                       AuthenticationManager authenticationManager, EmailService emailService){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    @Transactional
    public UserDTO registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DisneyRequestException("El email ya existe", "bad.request", HttpStatus.BAD_REQUEST);
        }
        RoleModel roleModel = roleRepository.findByName("ADMIN");
        user.setRole(RoleMapper.mapModelToDomain(roleModel));
        UserModel userModel = UserMapper.mapDomainToModel(user);
        userModel.setPassword(encryptPassword(user));
        UserModel save = userRepository.save(userModel);
        User userDomain = mapModelToDomain(save);
        emailService.welcomeEmail(user.getEmail());
        return UserMapper.mapDomainToDTO(userDomain);
    }

    public JwtDTO generateAuthenticationToken(User userDomain) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDomain.getEmail(), userDomain.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        MainUser userLog = (MainUser) authentication.getPrincipal();
        return new JwtDTO(jwt, userLog.getEmail());
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
    public User loginUser(User user) throws DisneyRequestException{
        if (userRepository.existsByEmail(user.getEmail())) {
            String password = user.getPassword();
            UserModel userModel = userRepository.findByEmail(user.getEmail());
            return getUserPasswordChecked(password, userModel);
        } else {
            throw new DisneyRequestException("Wrong username or password", "invalid.access", HttpStatus.UNAUTHORIZED);
        }

    }

    private User getUserPasswordChecked(String password, UserModel userModel) throws DisneyRequestException {
        if (passwordMatches(password, userModel.getPassword())) {
            User userDomain = mapModelToDomain(userModel);
            return userDomain;
        } else {
            throw new DisneyRequestException("Wrong username or password", "invalid.access", HttpStatus.UNAUTHORIZED);
        }
    }

    private Boolean passwordMatches(String password, String passwordEncrypted) {
        return passwordEncoder.matches(password, passwordEncrypted);
    }

    @Transactional
    public void deleteUser(Integer id) throws DisneyRequestException {
        Optional<UserModel> modelOptional = userRepository.findById(id);
        if (!modelOptional.isEmpty()) {
            UserModel userModel = modelOptional.get();
            userRepository.delete(userModel);
        } else {
            throw new DisneyRequestException("User not found", "not.found", HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public User updateUser(Integer id,User user) throws DisneyRequestException{
        if(userRepository.existsById(id)){
            UserModel userModel = userRepository.findById(Integer.valueOf(id)).get();
            userModel.setFirstName(user.getFirstName());
            userModel.setLastName(user.getLastName());
            userModel.setEmail(user.getEmail());
            userModel.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(userModel);
            return mapModelToDomain(userModel);
        }else {
            throw new DisneyRequestException("User not found", "not.found", HttpStatus.NOT_FOUND);
        }

    }

}
