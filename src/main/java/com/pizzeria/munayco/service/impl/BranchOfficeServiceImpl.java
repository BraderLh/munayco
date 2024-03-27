package com.pizzeria.munayco.service.impl;

import com.pizzeria.munayco.aggregates.constants.Constants;
import com.pizzeria.munayco.aggregates.request.RequestBranchOffice;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.entity.BranchOfficesEntity;
import com.pizzeria.munayco.repository.BranchOfficesRepository;
import com.pizzeria.munayco.service.BranchOfficesService;
import com.pizzeria.munayco.util.BranchOfficeValidations;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class BranchOfficeServiceImpl implements BranchOfficesService {
    private final BranchOfficesRepository branchOfficesRepository;
    private final BranchOfficeValidations branchOfficeValidations;

    public BranchOfficeServiceImpl(BranchOfficesRepository branchOfficesRepository, BranchOfficeValidations branchOfficeValidations) {
        this.branchOfficesRepository = branchOfficesRepository;
        this.branchOfficeValidations = branchOfficeValidations;
    }

    @Override
    public ResponseBase createBranchOffice(RequestBranchOffice requestBranchOffice) {
        boolean validateInput = branchOfficeValidations.validateInput(requestBranchOffice);
        if (validateInput) {
            BranchOfficesEntity branchOfficesEntity = getEntityCreate(requestBranchOffice);
            branchOfficesRepository.save(branchOfficesEntity);
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(branchOfficesEntity));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
        }
    }

    private BranchOfficesEntity getEntityCreate(RequestBranchOffice requestBranchOffice) {
        BranchOfficesEntity branchOfficesEntity = new BranchOfficesEntity();
        branchOfficesEntity.setAddress(requestBranchOffice.getAddress());
        branchOfficesEntity.setImage(requestBranchOffice.getImage());
        branchOfficesEntity.setLocation(requestBranchOffice.getLocation());
        branchOfficesEntity.setOpeningHours(requestBranchOffice.getOpeningHours());
        branchOfficesEntity.setPhone(requestBranchOffice.getPhone());
        branchOfficesEntity.setStatus(Constants.STATUS_ACTIVE);
        branchOfficesEntity.setTelephone(requestBranchOffice.getTelephone());
        branchOfficesEntity.setUserCreate(Constants.AUDIT_ADMIN);
        branchOfficesEntity.setDateCreate(new Timestamp(System.currentTimeMillis()));
        return branchOfficesEntity;
    }

    @Override
    public ResponseBase deleteBranchOfficeById(Integer id) {
        if (branchOfficesRepository.existsById(id)) {
            Optional<BranchOfficesEntity> branchOfficesEntity = branchOfficesRepository.findById(id);
            if (branchOfficesEntity.isPresent()) {
                BranchOfficesEntity branchOfficesDeleted = getEntityDelete(branchOfficesEntity.get());
                branchOfficesRepository.save(branchOfficesDeleted);
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(branchOfficesDeleted));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_NOT_DELETE, Optional.empty());
            }

        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' +  Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    private BranchOfficesEntity getEntityDelete(BranchOfficesEntity branchOfficesEntity) {
        branchOfficesEntity.setDateDelete(new Timestamp(System.currentTimeMillis()));
        branchOfficesEntity.setStatus(Constants.STATUS_INACTIVE);
        branchOfficesEntity.setUserDelete(Constants.AUDIT_ADMIN);
        return branchOfficesEntity;
    }

    @Override
    public ResponseBase findBranchOfficeById(Integer id) {
        if (branchOfficesRepository.existsById(id)) {
            Optional<BranchOfficesEntity> branchOfficesEntity = branchOfficesRepository.findById(id);
            if (branchOfficesEntity.isPresent()) {
                return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(branchOfficesEntity));
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_NOT_FIND, Optional.empty());
            }
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    @Override
    public ResponseBase findAllBranchOffices() {
        List<BranchOfficesEntity> branchOfficesEntityList = branchOfficesRepository.findAll();
        if (!branchOfficesEntityList.isEmpty()) {
            return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(branchOfficesEntityList));
        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ZERO_ROWS, Optional.empty());
        }
    }

    @Override
    public ResponseBase updateBranchOfficeById(Integer id, RequestBranchOffice requestBranchOffice) {
        if (branchOfficesRepository.existsById(id)) {
            Optional<BranchOfficesEntity> branchOfficesEntity = branchOfficesRepository.findById(id);
            if (branchOfficesEntity.isPresent()) {
                boolean validateInput = branchOfficeValidations.validateInput(requestBranchOffice);
                if (validateInput) {
                    BranchOfficesEntity branchOfficesUpdated = getEntityUpdate(branchOfficesEntity.get(), requestBranchOffice);
                    branchOfficesRepository.save(branchOfficesUpdated);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(branchOfficesUpdated));
                } else {
                    return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_DATA_NOT_VALID, Optional.empty());
                }
            } else {
                return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_ERROR_NOT_UPDATE, Optional.empty());
            }

        } else {
            return new ResponseBase(Constants.CODE_ERROR, Constants.MESS_ERROR + ' ' + Constants.MESS_NOT_FOUND_ID, Optional.empty());
        }
    }

    private BranchOfficesEntity getEntityUpdate(BranchOfficesEntity branchOfficesEntity, RequestBranchOffice requestBranchOffice) {
        branchOfficesEntity.setAddress(requestBranchOffice.getAddress());
        branchOfficesEntity.setImage(requestBranchOffice.getImage());
        branchOfficesEntity.setLocation(requestBranchOffice.getLocation());
        branchOfficesEntity.setPhone(requestBranchOffice.getPhone());
        branchOfficesEntity.setOpeningHours(requestBranchOffice.getOpeningHours());
        branchOfficesEntity.setTelephone(requestBranchOffice.getTelephone());
        branchOfficesEntity.setDateModify(new Timestamp(System.currentTimeMillis()));
        branchOfficesEntity.setUserModify(Constants.AUDIT_ADMIN);
        return branchOfficesEntity;
    }
}
