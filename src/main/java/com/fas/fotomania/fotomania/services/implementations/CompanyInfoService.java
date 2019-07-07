package com.fas.fotomania.fotomania.services.implementations;

import com.fas.fotomania.fotomania.entities.CompanyInfo;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.repository.ICompanyInfoRepository;
import com.fas.fotomania.fotomania.repository.IUserRepository;
import com.fas.fotomania.fotomania.services.interfaces.ICompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyInfoService implements ICompanyInfoService {

    @Autowired
    ICompanyInfoRepository companyInfoRepository;

    @Autowired
    IUserRepository userRepository;

    @Override
    public boolean saveCompanyInfo(CompanyInfo companyInfo) {
        boolean good=false;
        try {
            companyInfoRepository.save(companyInfo);
            good=true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return good;
    }

    @Override
    public boolean updateCompanyInfo(CompanyInfo companyInfo) {
        boolean good=false;
        try {
            companyInfoRepository.save(companyInfo);
            good=true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return good;
    }

    @Override
    public boolean deleteCompanyInfo(CompanyInfo companyInfo) {
        boolean good=false;
        try {
            companyInfoRepository.delete(companyInfo);
            good=true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return good;
    }

    @Override
    public List<CompanyInfo> listCompanyInfos() {
        return companyInfoRepository.findAll();
    }

    @Override
    public CompanyInfo findCompanyInfosByCompany(int companyId){
        Optional<User> currentCompany=userRepository.findById(companyId);
        return companyInfoRepository.findByUser(currentCompany);
    }

    @Override
    public Optional<CompanyInfo> findById(int id) {
        return companyInfoRepository.findById(id);
    }
}
