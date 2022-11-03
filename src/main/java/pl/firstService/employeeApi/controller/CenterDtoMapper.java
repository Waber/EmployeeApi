package pl.firstService.employeeApi.controller;

import org.modelmapper.ModelMapper;
import pl.firstService.employeeApi.dto.CenterCreateDto;
import pl.firstService.employeeApi.dto.CenterDto;
import pl.firstService.employeeApi.model.Center;

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
