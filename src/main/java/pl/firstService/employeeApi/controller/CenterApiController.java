package pl.firstService.employeeApi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.firstService.employeeApi.service.CenterService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/center")
public class CenterApiController {
    private CenterDtoMapper centerDtoMapper;
    private CenterService centerService;
}
