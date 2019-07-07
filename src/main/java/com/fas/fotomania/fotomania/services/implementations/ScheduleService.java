package com.fas.fotomania.fotomania.services.implementations;

import com.fas.fotomania.fotomania.entities.Schedule;
import com.fas.fotomania.fotomania.entities.User;
import com.fas.fotomania.fotomania.repository.IScheduleRepository;
import com.fas.fotomania.fotomania.repository.IUserRepository;
import com.fas.fotomania.fotomania.services.interfaces.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService implements IScheduleService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IScheduleRepository scheduleRepository;

    @Override
    public boolean saveSchedule(Schedule schedule) {
        boolean good=false;
        try {
            scheduleRepository.save(schedule);
            good=true;
        } catch (Exception e){
            System.out.println(e);
        }
        return good;
    }

    @Override
    public boolean updateSchedule(Schedule schedule) {
        boolean good=false;
        try {
            scheduleRepository.save(schedule);
            good=true;
        } catch (Exception e){
            System.out.println(e);
        }
        return good;
    }

    @Override
    public boolean deleteSchedule(Schedule schedule) {
        boolean good=false;
        try {
            scheduleRepository.delete(schedule);
            good=true;
        } catch (Exception e){
            System.out.println(e);
        }
        return good;
    }

    @Override
    public List<Schedule> listSchedule() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule findScheduleByCompany(int companyId) {
        Optional<User> currentUser= userRepository.findById(companyId);
        return scheduleRepository.findByUser(currentUser);
    }

    @Override
    public Optional<Schedule> findById(int id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public List<String> daysOfWork(int companyId) {
        List<String> daysOfWork= new ArrayList<String>();
        Schedule schedule = findScheduleByCompany(companyId);
        if(schedule != null){
            if(schedule.isWednesday()){
                daysOfWork.add("Wednesday");
            }
            if(schedule.isTuesday()){
                daysOfWork.add("Tuesday");
            }
            if(schedule.isThursday()){
                daysOfWork.add("Thursday");
            }
            if(schedule.isSunday()){
                daysOfWork.add("Sunday");
            }
            if(schedule.isSaturday()){
                daysOfWork.add("Saturday");
            }
            if(schedule.isMonday()){
                daysOfWork.add("Monday");
            }
            if(schedule.isFriday()){
                daysOfWork.add("Friday");
            }
        }
        return daysOfWork;
    }
}
