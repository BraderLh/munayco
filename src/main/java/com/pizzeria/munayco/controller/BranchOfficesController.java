package com.pizzeria.munayco.controller;

import com.pizzeria.munayco.aggregates.request.RequestBranchOffice;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.service.BranchOfficesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/branchOffice")
public class BranchOfficesController {
    private final BranchOfficesService branchOfficesService;

    public BranchOfficesController(BranchOfficesService branchOfficesService) {
        this.branchOfficesService = branchOfficesService;
    }

    @PostMapping
    public ResponseBase createBranchOffice(@RequestBody RequestBranchOffice requestBranchOffice) {
        return branchOfficesService.createBranchOffice(requestBranchOffice);
    }

    @DeleteMapping("{id}")
    public ResponseBase deleteBranchOffice(@PathVariable int id) {
        return branchOfficesService.deleteBranchOfficeById(id);
    }

    @GetMapping("{id}")
    public ResponseBase findOneBranchOffice(@PathVariable int id) {
        return branchOfficesService.findBranchOfficeById(id);
    }

    @GetMapping
    public ResponseBase findAllBranchOffices() {
        return branchOfficesService.findAllBranchOffices();
    }

    @PatchMapping("{id}")
    public ResponseBase updateBranchOffice(@PathVariable int id, @RequestBody RequestBranchOffice requestBranchOffice) {
        return branchOfficesService.updateBranchOfficeById(id, requestBranchOffice);
    }
}
