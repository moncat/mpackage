package com.co.example;

public class test2 {
	public static void main(String[] args) {
		 StringBuffer sql = new StringBuffer("SELECT\n" +
	                "	a.customer_id,\n" +
	                "	a.customer_name,\n" +
	                "	a.wx_nickname,\n" +
	                "	a.wx_remark,\n" +
	                "	b.tags,\n" +
	                "	c.reminds,\n" +
	                "	a.source_wx_id,\n" +
	                "	a.del_flag\n" +
	                "FROM\n" +
	                "	tf_f_customer a\n" +
	                "LEFT JOIN (\n" +
	                "	SELECT\n" +
	                "		a.customer_id,\n" +
	                "		GROUP_CONCAT(\n" +
	                "			DISTINCT b.tag_id,\n" +
	                "			'-#',\n" +
	                "			d.sys_flag,"
	                + "         '-#',c.show_flag,"
	                + "			'-#',c.tag_name\n" +
	                "		) AS tags\n" +
	                "	FROM\n" +
	                "		tf_f_customer a\n" +
	                "	LEFT JOIN tf_f_cust_tag_rel b ON a.customer_id = b.customer_id\n" +
	                "	AND b.del_flag = '0'\n" +
	                "	LEFT JOIN td_b_tag c ON b.tag_id = c.tag_id\n" +
	                "	AND c.del_flag = '0'\n" +
	                "	LEFT JOIN td_b_tag_category d ON c.tag_category_id = d.tag_category_id\n" +
	                "	WHERE\n" +
	                "		a.source_wx_id in :wxCodeId\n");

	            sql.append(" and a.customer_id in :customerIds\n ");

	        sql.append("	GROUP BY\n" +
	                "		a.customer_id\n" +
	                ") b ON a.customer_id = b.customer_id\n" +
	                "LEFT JOIN (\n" +
	                "	SELECT\n" +
	                "		a.customer_id,\n" +
	                "		GROUP_CONCAT(b.remind_id) AS reminds\n" +
	                "	FROM\n" +
	                "		tf_f_customer a\n" +
	                "	LEFT JOIN tf_f_cust_remind b ON a.customer_id = b.customer_id\n" +
	                "	WHERE\n" +
	                "		a.source_wx_id in :wxCodeId\n");
	
	            sql.append(" and a.customer_id in :customerIds\n");
	
	        sql.append(
	                "	AND b.complete_flag = '0'\n" +
	                        "	AND b.del_flag = '0'\n" +
	                        "	AND b.remind_time <= :today\n" +
	                        "	GROUP BY\n" +
	                        "		a.customer_id\n" +
	                        ") c ON a.customer_id = c.customer_id\n" +
	                        "WHERE\n" +
	                        "	source_wx_id in :wxCodeId\n");

	            sql.append(" and a.customer_id in :customerIds");
	      
	            sql.append(" order by del_flag ,pass_time desc");
	        
	            System.out.println(sql);
	}
	
	
	
}
