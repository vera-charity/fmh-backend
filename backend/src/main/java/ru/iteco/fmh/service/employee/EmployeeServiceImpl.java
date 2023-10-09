package ru.iteco.fmh.service.employee;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.EmployeeRepository;
import ru.iteco.fmh.dao.repository.PositionRepository;
import ru.iteco.fmh.dto.employee.*;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.employee.Employee;
import ru.iteco.fmh.model.employee.Position;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ConversionService conversionService;

    private final PositionRepository positionRepository;


    @Override
    public EmployeeInfoDto getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(e -> conversionService.convert(e, EmployeeInfoDto.class))
                .orElseThrow(() -> new NotFoundException("Cотрудник не найден"));

    }

    @Override
    public EmployeeInfoDto updateEmployee(int id, EmployeeChangingRequest employeeChangingRequest) {
        Optional<Employee> employeeFindById = employeeRepository.findById(id);

        if (employeeFindById.isEmpty()) {
            throw new NotFoundException("не найден");
        }
        Employee employee = employeeFindById.get();
        employee.getProfile().setFirstName(employeeChangingRequest.getFirstName());
        employee.getProfile().setLastName(employeeChangingRequest.getLastName());


        Optional<Position> position = positionRepository.findById(employeeChangingRequest.getPositionId());
        position.ifPresent(employee::setPosition);

        if (employeeChangingRequest.getScheduleStartDate() != null) {
            employee.setScheduleStartDate(employeeChangingRequest.getScheduleStartDate());
        }
        if (employeeChangingRequest.getWorkEndTime() != null) {
            employee.setWorkEndTime(employeeChangingRequest.getWorkEndTime());
        }
        if (employeeChangingRequest.getWorkStartTime() != null) {
            employee.setWorkStartTime(employeeChangingRequest.getWorkStartTime());
        }
        if (employeeChangingRequest.getScheduleType() != null) {
            employee.setScheduleType(employeeChangingRequest.getScheduleType());
        }
        if (employeeChangingRequest.getMiddleName() != null) {
            employee.getProfile().setMiddleName(employeeChangingRequest.getMiddleName());
        }

       employeeRepository.save(employee);

        return conversionService.convert(employee, EmployeeInfoDto.class);
    }

    @Override
    @Transactional
    public List<EmployeeInfoScheduleDto> getEmployeeList(String fullName, LocalDate startDate, LocalDate endDate,
                                                         boolean isActiveOnly, boolean returnWorkTime) {

        List<Employee> listEmployee = employeeRepository.findListEmployee(fullName, startDate, endDate, isActiveOnly);
        List<EmployeeInfoScheduleDto> employeeInfoScheduleDtos = (List<EmployeeInfoScheduleDto>) conversionService.convert(listEmployee,
                TypeDescriptor.collection(ArrayList.class, TypeDescriptor.valueOf(Employee.class)),
                TypeDescriptor.collection(ArrayList.class, TypeDescriptor.valueOf(EmployeeInfoScheduleDto.class)));
        return employeeInfoScheduleDtos;
    }
}
