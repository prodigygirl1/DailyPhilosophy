package com.studentproj.DailyPhilosophy.dao;

import com.studentproj.DailyPhilosophy.models.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
    Profile findByLogin(String login);
}
