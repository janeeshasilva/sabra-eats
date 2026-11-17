package com.example.sabra_eats.repository;

import com.example.sabra_eats.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Email සහ Password එක දීලා User කෙනෙක්ව හොයාගන්න
    User findByEmailAndPassword(String email, String password);

    // මේ Email එකෙන් කලින් එකවුන්ට් එකක් හදලා තියෙනවද බලන්න
    User findByEmail(String email);
}