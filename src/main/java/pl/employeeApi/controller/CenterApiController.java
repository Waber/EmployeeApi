package pl.employeeApi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import pl.employeeApi.dto.CenterCreateDto;
import pl.employeeApi.dto.CenterDto;
import pl.employeeApi.dto.CenterValidationErrorDto;
import pl.employeeApi.center.Center;
import pl.employeeApi.service.CenterService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/center")
public class CenterApiController {
    private final CenterDtoMapper centerDtoMapper = new CenterDtoMapper();
    private final CenterService centerService;

    //TODO dopisać validatory, actuator, testy jednostkowe, przerobić na zwrot Response Entity, post może zwracać tylko id ale link do geta
    //Hateoas dać wszędzie

    @GetMapping
    @Operation(summary = "Get list of centers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    public List<CenterDto> getCenters() {
        return centerService.getCenters(centerDtoMapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get center by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CenterDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Center not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    public ResponseEntity getCenterById(@PathVariable Long id) {
        return centerService.getCenterById(id, centerDtoMapper);
    }


    @PostMapping()
    @Operation(summary = "Create a center")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Center created", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CenterCreateDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CenterValidationErrorDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)})
    public ResponseEntity<CenterDto> createCenter(@RequestBody CenterCreateDto centerDto) {
        Center entity = centerDtoMapper.mapCreatedDtoToEntity(centerDto);

        UriComponents uriComponents = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(centerService.addCenter(entity).getId());
        return ResponseEntity.created(uriComponents.toUri()).body(centerDtoMapper.convertToDto(entity));
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Center updated", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CenterCreateDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CenterValidationErrorDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    public CenterDto updateCenterData(@RequestBody CenterDto centerDto, @PathVariable Long id) {
        Center centerUpdated = centerService.updateCenter(centerDtoMapper.convertDtoToEntity(centerDto), id);
        return centerDtoMapper.convertToDto(centerUpdated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete center by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Succesfully removed", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    public ResponseEntity deleteCenter(@PathVariable Long id) {
        centerService.deleteCenter(id);
        return ResponseEntity.noContent().build();
    }
}
