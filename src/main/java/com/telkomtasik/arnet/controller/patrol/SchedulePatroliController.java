package com.telkomtasik.arnet.controller.patrol;

import com.telkomtasik.arnet.dto.ResponseData;
import com.telkomtasik.arnet.dto.patrol.DTOSchedulePatroli;
import com.telkomtasik.arnet.model.patrol.SchedulePatroli;
import com.telkomtasik.arnet.service.patrol.SchedulePatroliService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/skso/schedulepatroli")
public class SchedulePatroliController {
    @Autowired
    private SchedulePatroliService schedulePatroliService;
    @Autowired
    private ResponseData responseData;
    @Autowired
    private ModelMapper modelMapper;

    //    get
    @GetMapping
    public ResponseEntity<Map> getAllData(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam(required = false) String nama) {

        return new ResponseEntity<Map>(
                schedulePatroliService.getAll(size, page, nama),
                new HttpHeaders(), HttpStatus.OK);
    }

    //    post
    @PostMapping
    public ResponseEntity<Map> createOne(@Valid @RequestBody DTOSchedulePatroli dtoSchedulePatroli, Errors errors) {
        if (errors.hasErrors()) {
            List<String> errorMessages = errors.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            Map<String, Object> response = responseData.failResponse(errorMessages, HttpStatus.BAD_REQUEST.value());
            HttpStatus httpStatus = HttpStatus.valueOf((Integer) response.get("status_code"));
            return new ResponseEntity<Map>(response, httpStatus);
        }
        Map checkpoint = schedulePatroliService.createOne(dtoSchedulePatroli);
        HttpStatus httpStatus = HttpStatus.valueOf((Integer) checkpoint.get("status_code"));

        return new ResponseEntity<Map>(checkpoint, httpStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map> updateOne(@PathVariable(value = "id") UUID id, @Valid @RequestBody DTOSchedulePatroli dtoSchedulePatroli, Errors errors) {

        if (errors.hasErrors()) {
            List<String> errorMessages = errors.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            Map<String, Object> response = responseData.failResponse(errorMessages, HttpStatus.BAD_REQUEST.value());
            HttpStatus httpStatus = HttpStatus.valueOf((Integer) response.get("status_code"));
            return new ResponseEntity<>(response, httpStatus);
        }

        SchedulePatroli currData = modelMapper.map(dtoSchedulePatroli, SchedulePatroli.class);
        currData.setId(id);

        Map response = schedulePatroliService.updateOne(currData);
        HttpStatus httpStatus = HttpStatus.valueOf((Integer) response.get("status_code"));

        return new ResponseEntity<Map>(response, httpStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map> removeOne(@PathVariable(value = "id") UUID id) {
        Map response = schedulePatroliService.removeById(id);
        HttpStatus httpStatus = HttpStatus.valueOf((Integer) response.get("status_code"));
        return new ResponseEntity<Map>(response, httpStatus);
    }
}
