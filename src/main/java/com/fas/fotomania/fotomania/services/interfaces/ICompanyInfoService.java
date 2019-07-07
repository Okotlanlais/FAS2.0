package com.fas.fotomania.fotomania.services.interfaces;

import com.fas.fotomania.fotomania.entities.CompanyInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ICompanyInfoService {
    public boolean saveCompanyInfo(CompanyInfo companyInfo);
    public boolean updateCompanyInfo(CompanyInfo companyInfo);
    public boolean deleteCompanyInfo(CompanyInfo companyInfo);
    public List<CompanyInfo> listCompanyInfos();
    public CompanyInfo findCompanyInfosByCompany(int companyId);
    public Optional<CompanyInfo> findById(int id);
}
