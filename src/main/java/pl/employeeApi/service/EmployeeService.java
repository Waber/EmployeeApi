package pl.employeeApi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import pl.employeeApi.controller.EmployeeDtoMapper;
import pl.employeeApi.dto.CreateEmployeeDto;
import pl.employeeApi.dto.EmployeeResponseDto;
import pl.employeeApi.employee.Employee;
import pl.employeeApi.employee.EmployeeValidationResult;
import pl.employeeApi.salaryHistory.SalaryHistory;
import pl.employeeApi.exception.EmployeeNotFoundException;
import pl.employeeApi.repository.EmployeeRepository;
import pl.employeeApi.repository.SalaryHistoryRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SalaryHistoryRepository salaryHistoryRepository;
    private final EmployeeDtoMapper employeeDtoMapper = new EmployeeDtoMapper();


    public List<EmployeeResponseDto> getEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeDtoMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeResponseDto> getEmployeeById(long id) {
        Optional<Employee> entity = Optional.ofNullable(employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee data object of id %d does not exist", id))));
        if (entity.isEmpty())
            return Optional.empty();

        return Optional.ofNullable(employeeDtoMapper.convertToDto(entity.get()));
        // message w ResponseUtil - użyty w kontrolerze
        // .orElseThrow(() -> new TypDomenyNotFoundException(TYPOBIEKTU_DOES_NOT_EXIST_MESSAGE + id));
//        Optional<DTO> dto= service.find(id);
//        return ResponseUtil.wrapOrNotFound(dto);
        //Poprawione, do sprawdzenia
    }

    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public EmployeeResponseDto addEmployee(CreateEmployeeDto employeeDto) {//TODO przyjać dto, zwraać dto + walidacja np pesel uniqueness - czy istnieje taki ziomek w bazie np
        //23.11 zmiana parametru metody
        Employee employee = employeeDtoMapper.convertDtoToEntity(employeeDto);
        EmployeeValidationResult validationResult = employee.validate();



        employee.setCreatedDate(LocalDate.now());
        employee.setLastModifiedDate(LocalDate.now());
        employeeRepository.save(employee);
        EmployeeResponseDto employeeResponseDto = employeeDtoMapper.convertToDto(employee);
        if(!validationResult.isValid()){
            employeeResponseDto.setValidationResult(validationResult);
        }
         return employeeResponseDto;
    }

    public boolean existsByPersonalId(String personalId){
        return employeeRepository.existsByPersonalId(personalId);
    }

    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployee(Employee employee, long id) {
        employee.setCreatedDate(employeeRepository.getById(id).getCreatedDate());
        employee.setLastModifiedDate(LocalDate.now());
        employee.setId(id);
        return employeeRepository.save(employee);
    }


    public boolean didEmployeeWorkInThisYear(Long employeeId, LocalDate year) {
        Optional<Employee> emploj = employeeRepository.findById(employeeId);
        if (emploj.get().getJobStartDate().isBefore(year)) {
            return true;
        }
        return false;
    }

    public BigDecimal calculateReceivedSalarySinceWorkBegin(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

        if (optionalEmployee.isEmpty()) {
            throw new NotFoundException("Employee not found");
        }

        Employee employee = optionalEmployee.get();
        List<SalaryHistory> salaryHistoryList = salaryHistoryRepository.findAllByEmployeeId(employeeId);

        BigDecimal totalSalary = BigDecimal.ZERO;
        BigDecimal monthlySalary = employee.getMonthlySalary();
        LocalDate employmentStartDate = employee.getJobStartDate();
        YearMonth startYearMonth = YearMonth.of(employmentStartDate.getYear(), employmentStartDate.getMonth());
        YearMonth endYearMonth = YearMonth.now();

        if (startYearMonth.isAfter(endYearMonth)) {
            throw new IllegalArgumentException("Employee not employed in the given year");
        }

        salaryHistoryList.sort(Comparator.comparing(SalaryHistory::getChangeDate));

        YearMonth lastChangeYearMonth = startYearMonth;
        BigDecimal salary = monthlySalary;
        BigDecimal monthsWorked = BigDecimal.ZERO;
        BigDecimal salaryForPeriod = BigDecimal.ZERO;

        for (SalaryHistory salaryHistory : salaryHistoryList) {
            LocalDate changeDate = salaryHistory.getChangeDate();
            salary = salaryHistory.getSalary();

            if (changeDate.isAfter(startYearMonth.atDay(1)) && changeDate.isBefore(endYearMonth.plusMonths(1).atDay(1))) {
                monthsWorked = BigDecimal.valueOf(lastChangeYearMonth.until(changeDate.plusMonths(1), ChronoUnit.MONTHS));
                salaryForPeriod = salary.multiply(monthsWorked);
                totalSalary = totalSalary.add(salaryForPeriod);

                lastChangeYearMonth = YearMonth.from(changeDate);
            }
        }

        monthsWorked = BigDecimal.valueOf(lastChangeYearMonth.until(endYearMonth.plusMonths(1), ChronoUnit.MONTHS));
        salaryForPeriod = salary.multiply(monthsWorked);
        totalSalary = totalSalary.add(salaryForPeriod);

        return totalSalary;
    }


    public BigDecimal calculateAverageSalaryByYear(Long employeeId, int year) {
        Optional<Employee> optionalEmployee = findById(employeeId);

        if (optionalEmployee.isEmpty()) {
            throw new NotFoundException("Employee not found");
        }
        Employee employee = optionalEmployee.get();
        List<SalaryHistory> salaryHistoryList = salaryHistoryRepository.findAllByEmployeeId(employeeId);

        BigDecimal totalSalary = BigDecimal.ZERO;
        int numberOfMonths = 0;
        BigDecimal monthlySalary = employee.getMonthlySalary();
        LocalDate employmentStartDate = employee.getJobStartDate();
        YearMonth startYearMonth = YearMonth.of(employmentStartDate.getYear(), employmentStartDate.getMonth());
        YearMonth endYearMonth = YearMonth.of(year, 12);

        if (startYearMonth.isAfter(endYearMonth)) {
            throw new IllegalArgumentException("Employee not employed in the given year");
        }

        //if employee was employeed earlier, we can use months since beginning of the year
        if (startYearMonth.getYear() != endYearMonth.getYear()) {
            startYearMonth = YearMonth.of(year, 1);
        }

        //if we are calculating for current year, we have to use current month as last one
        if (endYearMonth.getYear() == YearMonth.now().getYear()) {
            endYearMonth = YearMonth.of(year, YearMonth.now().getMonth());
        }
        for (SalaryHistory salaryHistory : salaryHistoryList) {
            LocalDate changeDate = salaryHistory.getChangeDate();
            BigDecimal salary = salaryHistory.getSalary();

            if (changeDate.getYear() == year && changeDate.getMonthValue() == startYearMonth.getMonthValue()) {
                // Zmiana wynagrodzenia od początku roku
                BigDecimal monthsWorked = BigDecimal.valueOf(startYearMonth.until(endYearMonth, ChronoUnit.MONTHS) + 1);
                BigDecimal salaryForYear = salary.multiply(monthsWorked);
                totalSalary = totalSalary.add(salaryForYear);
                numberOfMonths += monthsWorked.intValue();
                break;
            } else if (changeDate.isAfter(startYearMonth.atDay(1)) && changeDate.isBefore(endYearMonth.plusMonths(1).atDay(1))) {
                // Zmiana wynagrodzenia w trakcie roku
                BigDecimal monthsWorked = BigDecimal.valueOf(changeDate.until(endYearMonth.plusMonths(1), ChronoUnit.MONTHS));
                BigDecimal salaryForYear = salary.multiply(monthsWorked);
                totalSalary = totalSalary.add(salaryForYear);
                numberOfMonths += monthsWorked.intValue();
            }
        }

        if (totalSalary.equals(BigDecimal.ZERO)) {
            // Brak zmian wynagrodzenia, używamy aktualnego wynagrodzenia
            BigDecimal monthsWorked = BigDecimal.valueOf(startYearMonth.until(endYearMonth, ChronoUnit.MONTHS) + 1);
            totalSalary = monthlySalary.multiply(monthsWorked);
            numberOfMonths += monthsWorked.intValue();
        }

        return totalSalary.divide(BigDecimal.valueOf(numberOfMonths), 2, RoundingMode.HALF_UP);

    }


}
