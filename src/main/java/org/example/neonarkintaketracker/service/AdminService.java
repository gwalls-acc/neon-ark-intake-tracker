package org.example.neonarkintaketracker.service;
import org.example.neonarkintaketracker.dto.UserAdminResponse;
import org.example.neonarkintaketracker.entity.Role;
import org.example.neonarkintaketracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    public List<UserAdminResponse> getAllUsersForGovernance() {
        return userRepository.findAllWithRoles().stream()
                .map(user -> new UserAdminResponse(
                        user.getFullName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getRoles().stream().map(Role::getName).toList()
                ))
                .toList();
    }
}
