package com.epam.esm.service.impl;

import com.epam.esm.core.entity.User;
import com.epam.esm.core.exception.NotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.epam.esm.core.constants.ErrorMessageConstants.USER_NOT_FOUND_ERROR_MESSAGE;
import static com.epam.esm.core.utils.Validator.validatePageAndSize;

/**
 * Implementation of the {@link UserService} interface.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll(int page, int size) {
        validatePageAndSize(page, size, User.class);

        return userRepository.findAll(page, size);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + id, User.class));
    }

}