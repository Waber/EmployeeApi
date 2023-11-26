package pl.employeeApi.controller;

import org.modelmapper.ModelMapper;
import pl.employeeApi.center.Center;
import pl.employeeApi.dto.CenterCreateDto;
import pl.employeeApi.dto.CenterDto;

public class CenterDtoMapper {
    private  ModelMapper modelMapper = new ModelMapper();

    public  CenterDto convertToDto(Center center){
        return modelMapper.map(center, CenterDto.class);
    }

    public  Center convertDtoToEntity(CenterDto centerDto){
        return modelMapper.map(centerDto, Center.class);
    }

    public  Center mapCreatedDtoToEntity(CenterCreateDto centerCreateDto){
        return modelMapper.map(centerCreateDto, Center.class);
    }
}
