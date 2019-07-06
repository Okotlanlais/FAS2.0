package com.fas.fotomania.fotomania.services.interfaces;

import com.fas.fotomania.fotomania.entities.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IScheduleService {
    public boolean saveSchedule(Schedule schedule);
    public boolean updateSchedule(Schedule schedule);
    public boolean deleteSchedule(Schedule schedule);
    public List<Schedule> listSchedule();
    public Schedule findScheduleByCompany(int companyId);
    public Optional<Schedule> findById(int id);
    public List<String> daysOfWork(int companyId);
}
