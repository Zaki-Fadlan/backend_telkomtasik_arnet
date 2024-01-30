package com.telkom.psb_tools.controller.datamaster;

import com.telkom.psb_tools.model.datamaster.Datel;
import com.telkom.psb_tools.service.datamaster.DatelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("v1/sto")
public class StoController {
    @Autowired
    DatelService datelService;

    @PostMapping("/add")
    private ResponseEntity<Map> addData(@RequestBody Datel objModel) {
        Map obj = datelService.addDatel(objModel);
        return new ResponseEntity<Map>(obj, HttpStatus.OK);
    }

    @GetMapping
    private String getData() {
        return "Hello Datel Get ";
    }

    @GetMapping("/{id}")
    private String getDataById() {
        return "Hello Datel Get by ID";
    }

    @PutMapping("/update")
    private String editData() {
        return "Hello Datel update";
    }

    @DeleteMapping("/delete/{id}")
    private String deleteData() {
        return "Hello Datel delete";
    }
}
