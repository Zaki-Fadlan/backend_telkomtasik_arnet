package com.telkomtasik.arnet.controller.patrol;

import com.telkomtasik.arnet.dto.ResponseData;
import com.telkomtasik.arnet.dto.patrol.DTOReportPatroli;
import com.telkomtasik.arnet.service.patrol.ReportPatroliService;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/skso/reportpatroli")
public class ReportPatroliController {
    @Autowired
    private ReportPatroliService reportPatroliService;
    @Autowired
    private ResponseData responseData;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Map> getAllData(
            @RequestParam() Integer page,
            @RequestParam() Integer size,
            @RequestParam(required = false) String nama) {

        return new ResponseEntity<Map>(
                reportPatroliService.getAll(size, page, nama),
                new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map> createOne(@Valid @RequestBody DTOReportPatroli dtoReportPatroli, Errors errors) {
        if (errors.hasErrors()) {
            List<String> errorMessages = errors.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            Map<String, Object> response = responseData.failResponse(errorMessages, HttpStatus.BAD_REQUEST.value());
            HttpStatus httpStatus = HttpStatus.valueOf((Integer) response.get("status_code"));
            return new ResponseEntity<Map>(response, httpStatus);
        }
        Map response = reportPatroliService.createOne(dtoReportPatroli);
        HttpStatus httpStatus = HttpStatus.valueOf((Integer) response.get("status_code"));
        return new ResponseEntity<Map>(response, httpStatus);
    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Map> updateOne(@PathVariable(value = "id") Long id, @Valid @RequestBody DTOReportPatroli dtoReportPatroli, Errors errors) {
//
//        if (errors.hasErrors()) {
//            List<String> errorMessages = errors.getAllErrors()
//                    .stream()
//                    .map(ObjectError::getDefaultMessage)
//                    .collect(Collectors.toList());
//
//            Map<String, Object> response = responseData.failResponse(errorMessages, HttpStatus.BAD_REQUEST.value());
//            HttpStatus httpStatus = HttpStatus.valueOf((Integer) response.get("status_code"));
//            return new ResponseEntity<>(response, httpStatus);
//        }
//        Re.se(id);
//        Map response = reportPatroliService.updateOne(dtoReportPatroli);
//        HttpStatus httpStatus = HttpStatus.valueOf((Integer) response.get("status_code"));
//        return new ResponseEntity<Map>(response, httpStatus);
//    }
//
//    //        delete
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Map> removeOne(@PathVariable(value = "id") Long id) {
//        Map response = statusService.removeById(id);
//        HttpStatus httpStatus = HttpStatus.valueOf((Integer) response.get("status_code"));
//        return new ResponseEntity<Map>(response, httpStatus);
//    }
}
