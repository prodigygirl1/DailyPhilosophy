package com.studentproj.DailyPhilosophy.mapper;
import com.studentproj.DailyPhilosophy.dto.RegisterDto;
import com.studentproj.DailyPhilosophy.models.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile fromRegisterDto(RegisterDto dto);
}
