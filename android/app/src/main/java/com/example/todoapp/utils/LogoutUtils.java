package com.example.todoapp.utils;

import com.example.todoapp.data.network.todoapp.TodoService;
import com.example.todoapp.data.repository.app.AppRepository;
import com.example.todoapp.data.repository.keystore.KeyStoreRepository;
import com.example.todoapp.data.repository.user.UserRepository;

public class LogoutUtils {
    private final UserRepository mUserRepository;
    private final AppRepository mAppRepository;
    private final KeyStoreRepository mKeyStoreRepository;
    private final TodoService mTodoService;


    public LogoutUtils(UserRepository mUserRepository, AppRepository mAppRepository,
                       KeyStoreRepository mKeyStoreRepository, TodoService mTodoService) {
        this.mUserRepository = mUserRepository;
        this.mAppRepository = mAppRepository;
        this.mKeyStoreRepository = mKeyStoreRepository;
        this.mTodoService = mTodoService;
    }

    public void logout() {
        mUserRepository.logout();
        mAppRepository.clear();
        mKeyStoreRepository.clearCache();
        mTodoService.clearCache();
    }
}
