package com.sparta.workflowhelper.domain.user.service;

import com.sparta.workflowhelper.domain.user.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdapter userAdapter;
}
