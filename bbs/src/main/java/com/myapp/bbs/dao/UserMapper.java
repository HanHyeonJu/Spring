package com.myapp.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.myapp.bbs.model.User;

/**
 * 매퍼 클래스
 * board와 달리 loign쪽은 매퍼 XML 파일 없이 이 자바 매퍼에서 쿼리 처리 다함
 * 간단한 쿼리문만 필요하기 때문에 굳이 XML파일 필요없음
 */

@Mapper
public interface UserMapper {

	@Insert("INSERT INTO user VALUES(#{email}, #{password}, #{name})")
	public int insert(User user);

	@Update("UPDATE user SET password = #{password} ? email = #{email}")
	public int update(User user);

	@Delete("DELETE FROM user WHERE email = #{email}")
	public int delete(String email);

	@Select("SELECT ? (*) FROM user")
	public int count();

	@Select("SELECT * FROM user")
	public List<User> selectAll();

	@Select("SELECT * FROM user WHERE email = #{email} ")
	public User selectByEmail(String email);

}
