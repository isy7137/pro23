package com.spring.ex01;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;   // 마이바티스와 작업하는 주요 자바 인터페이스, 이 인터페이스를 통해 명령어 실행, 매퍼들 가져오기, 트랜잭션(DB all or nothing) 관리,    The primary Java interface for working with MyBatis. Through this interface you can execute commands, get mappers and manage transactions.
import org.apache.ibatis.session.SqlSessionFactory; // DB 접속(연결)을 위한 공장
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MemberDAO {

	//DB 연결 코드 작성
	
	public static SqlSessionFactory sqlMapper = null;
	
	//동적(필요시) 객체 생성하는 메서드
	
	private static SqlSessionFactory getInstance(){
		//sqlMapper 유효성(null 여부) 검사
		
		if(sqlMapper==null) {
			
			String resource = "mybatis/SqlMapConfig.xml";
			
			try (Reader  reader=Resources.getResourceAsReader(resource)){
				
				
				System.out.println("reader객체:" + reader);
				
				SqlSessionFactoryBuilder fb=new SqlSessionFactoryBuilder();
				sqlMapper = fb.build(reader);
//				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				System.out.println("sqlMapper 객체:" + sqlMapper);
//				reader.close();
				
			} catch (IOException e) {
				System.out.println("sqlMap 설정파일 불러오는데 있어서 에러남");
				//e.printStackTrace();
			}
			
		}
		
		
		return sqlMapper;
	}
	
	
	
	
	public List<MemberVO> selectAllMemberList(){
		sqlMapper = getInstance();
		
		SqlSession session= sqlMapper.openSession();
		
		List<MemberVO> memberList = session.selectList("mapper.member.selectAllMemberList");
		
		return memberList;
	}
	
	public void addMember(MemberVO memberVO) {		
		sqlMapper = getInstance();		
		SqlSession session= sqlMapper.openSession();
//		session.insert("mapper.member.addMember"); 매개변수 안넣으면 안됨
		session.insert("mapper.member.addMember",memberVO );
		session.commit(); //  sqlSession은 commit 꼭 해줘야 함
	}
	
	
	
	
	
	public void delMember(String id){
		sqlMapper = getInstance();		
		SqlSession session= sqlMapper.openSession();
		session.delete("mapper.member.delMember", id);
		session.commit();
	}
	
	
	
	
	
	public List<MemberVO> searchMember(String memberName){
		sqlMapper = getInstance();		
		SqlSession session= sqlMapper.openSession();
		List<MemberVO> memberList=session.selectList("mapper.member.searchMemberbyName", memberName);
		return memberList;
	}
	
	
	
	public MemberVO searchMemberID(String memberID){
		sqlMapper = getInstance();		
		SqlSession session= sqlMapper.openSession();
		MemberVO memberVO=session.selectOne("mapper.member.searchMemberbyID",  memberID);
		return memberVO;
	}
	
	
	
	
	
	
	public void updateMember(MemberVO memberVO){
		sqlMapper = getInstance();		
		SqlSession session= sqlMapper.openSession();
		session.update("mapper.member.updateMember", memberVO);
		session.commit();
	}
	
	
	
	
	public List<MemberVO> selectMemberByNameOrEmail(String nameOrEmail){
		System.out.println("memberDAO에서의 nameOrEmail : " + nameOrEmail);
		sqlMapper = getInstance();		
		SqlSession session= sqlMapper.openSession();
		List<MemberVO> memberList=session.selectList("mapper.member.selectMemberByNameOrEmail", nameOrEmail);
		return memberList;		
	}
	
	
	
	
	
	
	
	
	
}
