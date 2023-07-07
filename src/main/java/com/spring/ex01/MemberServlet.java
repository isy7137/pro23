package com.spring.ex01;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/member.do")
public class MemberServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request,response );
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request,response );
	}
	
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		MemberDAO dao = new MemberDAO();
		
		String action = request.getParameter("action");
		System.out.println("액션 값" + action);
		String nextPage = "";
		
		if(action ==null || action.equals("listMembers")) {
			
			List<MemberVO> membersList =dao.selectAllMemberList();		
			request.setAttribute("membersList", membersList);
			
			nextPage = "test01/listMembers.jsp";
			
		}else if(action.equals("addMember")){
			System.out.println("회원추가코드");
			
			String id=request.getParameter("id");
			String pwd=request.getParameter("pwd");
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			System.out.println(id+","+pwd+","+name+","+email);			
			MemberVO memberVO = new MemberVO(id, pwd, name, email);			
			dao.addMember(memberVO);						
			 nextPage="/member.do?action=listMembers";			
		}else if(action.equals("delMember")) {
			System.out.println("회원 삭제 코드 작성");
			String id=request.getParameter("id");
			System.out.println(id);
			
			dao.delMember(id);
			 nextPage="/member.do?action=listMembers";	
		}else if(action.equals("searchMember")) {
			System.out.println("회원 이름 검색 코드 작성");
			String memberName=request.getParameter("value");
			System.out.println(memberName);			
			List<MemberVO> membersList =dao.searchMember(memberName);			
			request.setAttribute("membersList", membersList);			
			nextPage = "test01/listMembers.jsp";			
		}else if(action.equals("selectMemberById")) {
			System.out.println("회원 아이디 검색 코드 작성");
			String memberID=request.getParameter("value");
			System.out.println(memberID);
			MemberVO member =dao.searchMemberID(memberID);			
			request.setAttribute("member", member);			
			nextPage = "test01/listMembers.jsp";
		}else if(action.equals("modMemberForm")) {
			System.out.println("회원 정보 수정 폼으로 가기");
			nextPage = "test01/modMemberForm.jsp";
		}else if(action.equals("modMember")) {
			System.out.println("회원 정보 수정 코드 작성");
			String id=request.getParameter("id");
			String pwd=request.getParameter("pwd");
	        String name=request.getParameter("name");
	        String email = request.getParameter("email");
	        System.out.println(id+","+pwd+","+name+","+email);			
			MemberVO memberVO = new MemberVO(id, pwd, name, email);	
			dao.updateMember(memberVO);
			 nextPage="/member.do?action=listMembers";
		}else if(action.equals("selectMemberByNameOrEmail")) {
			System.out.println("회원이름이나 이메일 중 둘  중 하나로 검색하기");
			String nameOrEmail=request.getParameter("value");
			System.out.println(nameOrEmail);
			List<MemberVO> membersList= dao.selectMemberByNameOrEmail(nameOrEmail);
			request.setAttribute("membersList", membersList);			
			nextPage = "test01/listMembers.jsp";	
		}
		
		
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);
	}

}
