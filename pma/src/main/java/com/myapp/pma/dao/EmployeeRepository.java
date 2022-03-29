package com.myapp.pma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.myapp.pma.dto.EmployeeProject;
import com.myapp.pma.entities.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> { // CrudRepository에 왠만한 CRUD메소드 존재
	// 자동으로 CRUD 객체 생성
	@Override
	List<Employee> findAll(); // 기존의 findAll()은 return타입이 Iterable<Employee>이라서 수정
	
	
	// 쿼리문을 실행하여 그 결과를 리스트로 return(dto에서 get메소드를 통해 받은 값을 저장한 객체들)
	// AS로 객체로 저장할 이름과 같이 만들어서 출력해줌
	@Query(nativeQuery = true, value = "SELECT LAST_NAME AS lastName ,FIRST_NAME AS firstName ,COUNT(PROJECT_ID) AS count "
			+ "FROM EMPLOYEE e JOIN PROJECT_EMPLOYEE  pe "
			+ "ON e.EMPLOYEE_ID = pe.EMPLOYEE_ID "
			+ "GROUP BY LAST_NAME ,FIRST_NAME, e.EMPLOYEE_ID "
			+ "ORDER BY count DESC" ) // left 조인 하면 프로젝트에 참여하지 않은 직원들도 출력
	public List<EmployeeProject> employeeProjects();  // employeeProjects = 메소드 이름 

}
