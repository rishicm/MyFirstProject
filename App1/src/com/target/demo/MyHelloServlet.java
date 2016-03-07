package com.target.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyHelloServlet
 */
@WebServlet("/MyHelloServlet")
public class MyHelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyHelloServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String s1 = request.getParameter("jobName");
		String s2 = request.getParameter("noOfKeys");
		String s3 = request.getParameter("tableName");
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		int value = 0;
		int newvalue = 0;
		// System.getProperty("java.class.path");
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			System.out.println("Before conn. " +dateFormat.format(new Date()));
			String connurl = "jdbc:teradata://TDPRODA/TMODE=ANS,CHARSET=UTF8,LOGMECH=LDAP";
			Class.forName("com.teradata.jdbc.TeraDriver");
			Connection conn = DriverManager.getConnection(connurl, "z001lcv",
					"6174!hiker");
			System.out.println("After conn. " +dateFormat.format(new Date()));
			String query = "select LAST_USE_SEQ_VAL_I from dltts_teradata.key_seq_test where ENT_PHYS_N ='"
					+ s3 + "' and SEQ_LK_JOB_N ='" + s1 + "'";

			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				value = rs.getInt(1);
				System.out.println("Value got from DB is =" + value);
			}
			 newvalue = value + Integer.parseInt(s2);
			String sql = "UPDATE dltts_teradata.key_seq_test SET LAST_USE_SEQ_VAL_I = "
					+ newvalue + " where ENT_PHYS_N ='" + s3
					+ "' and SEQ_LK_JOB_N ='" + s1 + "'";
			stmt = null;
			 stmt = conn.prepareStatement(sql);
			int num = stmt.executeUpdate();
			System.out.println("updated value is = "+newvalue);

		} catch (Exception e) {
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<h1>Hey , job done successfully. New value is :</h1>" + newvalue);
		out.println("</body>");
		out.println("</html>");
	}

}
